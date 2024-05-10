package it.polimi.parkingService.webApplication.parking.services;

import it.polimi.parkingService.webApplication.account.models.User;
import it.polimi.parkingService.webApplication.account.service.IUserService;
import it.polimi.parkingService.webApplication.parking.dao.ParkingSpotRepository;
import it.polimi.parkingService.webApplication.parking.enums.ParkingSpotStatus;
import it.polimi.parkingService.webApplication.parking.enums.ParkingStripes;
import it.polimi.parkingService.webApplication.parking.exceptions.ParkingNotTerminated;
import it.polimi.parkingService.webApplication.parking.exceptions.UnavailableParkingSpot;
import it.polimi.parkingService.webApplication.parking.models.Booking;
import it.polimi.parkingService.webApplication.parking.models.Parking;
import it.polimi.parkingService.webApplication.parking.models.ParkingArea;
import it.polimi.parkingService.webApplication.parking.models.ParkingSpot;
import it.polimi.parkingService.webApplication.parking.strategy.LinearSearchParkingSpotResearchStrategy;
import it.polimi.parkingService.webApplication.parking.strategy.ParkingSpotSearchCriteria;
import it.polimi.parkingService.webApplication.parking.strategy.PriorityQueueParkingSpotResearch;
import it.polimi.parkingService.webApplication.payment.models.PaymentReceipt;
import it.polimi.parkingService.webApplication.payment.models.PaymentSystem;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class ParkingSpotService implements IParkingSpotService{

    @Value("${parking.area.name}")
    private String parkingAreaName;

    private final ParkingSpotRepository parkingSpotRepository;

    private final IParkingService parkingService;

    private final IBookingService bookingService;

    private final IUserService userService;

    private final IQRCodeService qrCodeService;

    private final SseService sseService;

    private final IParkingAreaService parkingAreaService;

    private PaymentSystem paymentSystem;


    public ParkingSpotService(ParkingSpotRepository parkingSpotRepository, IParkingService parkingService, IBookingService bookingService, IUserService userService, IQRCodeService qrCodeService, SseService sseService, IParkingAreaService parkingAreaService, PaymentSystem paymentSystem) {
        this.parkingSpotRepository = parkingSpotRepository;
        this.parkingService = parkingService;
        this.bookingService = bookingService;
        this.userService = userService;
        this.qrCodeService = qrCodeService;
        this.sseService = sseService;
        this.parkingAreaService = parkingAreaService;
        this.paymentSystem = paymentSystem;
    }

    @Override
    public List<ParkingSpot> findAll() {
        return parkingSpotRepository.findAll();
    }

    @Override
    @Transactional
    public ParkingSpot findById(long id) {
        ParkingSpot spot = parkingSpotRepository.findById(id);

        if (spot == null) {
            throw new RuntimeException("Did not find parking spot - " + id);
        }

        return spot;
    }

    @Override
    public void toggleSpotAvailability(long id) {
        ParkingSpot spot = findById(id);
        if(spot.getStatus() == ParkingSpotStatus.UNAVAILABLE) {
            spot.setStatus(ParkingSpotStatus.FREE);
        } else {
            spot.setStatus(ParkingSpotStatus.UNAVAILABLE);
        }
        save(spot);
    }

    @Override
    public ParkingSpot save(ParkingSpot entity) {
        return parkingSpotRepository.save(entity);
    }

    @Override
    public void deleteById(long id) {
        parkingSpotRepository.deleteById(id);
    }


    @Override
    public void update(long id, ParkingSpotStatus status) {
        parkingSpotRepository.update(id, status);
    }

    public Map<ParkingSpot, Parking> getSpotsWithParkings() {
        List<ParkingSpot> parkingSpots = findAll();
        Map<ParkingSpot, Parking> pairs = new LinkedHashMap<>();

        List<Booking> bookings = bookingService.findByDate(LocalDate.now());
        List<Long> reservedSpots = new ArrayList<>();
        if(!bookings.isEmpty()) {
            for (Booking reservation : bookings) {
                if(!reservation.getClaimed()) {
                    reservedSpots.add(reservation.getParkingSpot().getId());
                }
            }
        }

        for(ParkingSpot spot : parkingSpots) {

            if(!bookings.isEmpty() && reservedSpots.contains(spot.getId())) {
                spot.setStatus(ParkingSpotStatus.RESERVED);
            }

            if(spot.getStatus() == ParkingSpotStatus.BUSY) {
                Parking parking = parkingService.findActualInProgressParkingBySpot(spot);
                pairs.put(spot, parking);
            } else {
                pairs.put(spot, null);
            }
        }
        return pairs;
    }

    public ParkingSpot findParkingSpot (String checkinToken){
        long userId = qrCodeService.getTokenPayload("userId", checkinToken);
        User user = userService.findById(userId);

        ParkingSpot parkingSpot = new ParkingSpot();
        Parking parking = new Parking(user);

        // check for reservation
        Booking booking = bookingService.findByCustomerUserAndClaimedFalseAndDate(user, LocalDate.now());
        if(booking != null && !booking.getClaimed()){
            parkingSpot = booking.getParkingSpot();
            booking.setClaimed(true);
            bookingService.update(booking.getId(), booking.getClaimed());
            parking.setBooking(booking);
        }
        else {
            ParkingArea parkingArea = parkingAreaService.findByName(parkingAreaName);

            parkingArea.setParkingSpots(bindSpotsWithDailyReservation(parkingArea.getParkingSpots(), LocalDate.now()));

            parkingSpot = getParkingSpot(parkingArea, user);
        }
        // update status and save
        parkingSpot.setStatus(ParkingSpotStatus.BUSY);
        update(parkingSpot.getId(), parkingSpot.getStatus());
        sseService.sendEvent(new Event(parkingSpot.getId(), parkingSpot.getStatus()));

        // create parking and save
        parking.setSpot(parkingSpot);
        parkingService.save(parking);

        return parkingSpot;
    }

    @Override
    public PaymentReceipt doCheckout(String token) throws ParkingNotTerminated {
        Parking parking = parkingService.findById(qrCodeService.getTokenPayload("parkingId", token));
        parking.setLeaving(LocalDateTime.now());

        PaymentReceipt receipt = new PaymentReceipt();
        if(parking.getBooking() == null) {
            parking.pay(paymentSystem);
            receipt = parking.getPaymentReceipt();
        } else
        {
            receipt = parking.getBooking().getPaymentReceipt();
        }
        parkingService.save(parking);
        ParkingSpot parkingSpot = parking.getSpot();
        parkingSpot.setStatus(ParkingSpotStatus.FREE);
        save(parkingSpot);
        sseService.sendEvent(new Event(parkingSpot.getId(), parkingSpot.getStatus()));
        return receipt;
    }

    @Override
    public Booking reserveParkingSpot(String username, Booking booking) {
        User user = userService.findByUserName(username);
        int reservations = bookingService.countBookingByCustomerUserAndDate(user, booking.getDate());
        Optional<Parking> parking = parkingService.findInProgressParkingsByUserId(user);
        if(reservations > 0) {
            throw new RuntimeException(username + " has already got reservation for " + booking.getDate());
        }
        if(parking.isPresent() && (parking.get().getArrival().toLocalDate().isEqual(booking.getDate()))) {
            throw new RuntimeException(username + " has a parking in progress for " + booking.getDate());
        }
        ParkingArea parkingArea = parkingAreaService.findByName(parkingAreaName);

        parkingArea.setParkingSpots(bindSpotsWithDailyReservation(parkingArea.getParkingSpots(), booking.getDate()));

//        for(ParkingSpot spot: parking.getParkingSpots()) {
//            if(spot.getStatus() != ParkingSpotStatus.RESERVED) {
//                spot.setStatus(ParkingSpotStatus.FREE);
//
//            }
//        }

        ParkingSpot spot = getParkingSpot(parkingArea, user);

        booking.setParkingSpot(spot);
        booking.setCustomerUser(user);
        booking.pay(paymentSystem);
        bookingService.save(booking);
        return booking;
    }

    private List<ParkingSpot> bindSpotsWithDailyReservation(List<ParkingSpot> spots, LocalDate date) {
        List<Booking> bookings = bookingService.findByDate(date);

        if(!bookings.isEmpty()) {
            List<Long> reservedSpots = new ArrayList<>();
            for (Booking reservation : bookings) {
                reservedSpots.add(reservation.getParkingSpot().getId());
            }

            for (ParkingSpot spot : spots) {
                if (reservedSpots.contains(spot.getId())) {
                    spot.setStatus(ParkingSpotStatus.RESERVED);
                }
            }
        }
        return spots;
    }


    private ParkingSpot getParkingSpot(ParkingArea parkingArea, User user ) {
        ParkingSpotSearchCriteria userSearchCriteria = getUserCriteria(user);

        parkingArea.setParkingSpotResearchStrategy(new LinearSearchParkingSpotResearchStrategy());

        ParkingSpot parkingSpot = parkingArea.searchForParkingSpot(userSearchCriteria);

        // if special spot are not available, search in normal spots, in quelli più vicini possibili
        if(parkingSpot == null && (user.getIsPregnant() || user.getIsDisabled())) {
            userSearchCriteria.setStripeColor(ParkingStripes.WHITE);
            parkingArea.setParkingSpotResearchStrategy(new PriorityQueueParkingSpotResearch());
            parkingSpot = parkingArea.searchForParkingSpot(userSearchCriteria);
        }

        if(parkingSpot == null) {
            throw new UnavailableParkingSpot("All parking spots are temporarily reserved");
        }

        return parkingSpot;
    }

    private ParkingSpotSearchCriteria getUserCriteria(User user) {
        ParkingSpotSearchCriteria userSearchCriteria = new ParkingSpotSearchCriteria();
        // is user has both disability and pregnancy condition, set disability as priority
        if(user.getIsPregnant()) {
            userSearchCriteria.setStripeColor(ParkingStripes.PINK);
        }
        if(user.getIsDisabled()) {
            userSearchCriteria.setStripeColor(ParkingStripes.YELLOW);
        }
        return userSearchCriteria;
    }

}
