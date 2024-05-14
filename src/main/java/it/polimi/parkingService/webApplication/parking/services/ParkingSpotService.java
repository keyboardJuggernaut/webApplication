package it.polimi.parkingService.webApplication.parking.services;

import it.polimi.parkingService.webApplication.account.models.User;
import it.polimi.parkingService.webApplication.account.services.IUserService;
import it.polimi.parkingService.webApplication.parking.dao.ParkingSpotRepository;
import it.polimi.parkingService.webApplication.parking.enums.ParkingSpotStatus;
import it.polimi.parkingService.webApplication.parking.enums.ParkingStripes;
import it.polimi.parkingService.webApplication.parking.exceptions.ParkingNotTerminated;
import it.polimi.parkingService.webApplication.parking.exceptions.ResourceNotFound;
import it.polimi.parkingService.webApplication.parking.exceptions.SearchStrategyUndefined;
import it.polimi.parkingService.webApplication.parking.exceptions.UnavailableParkingSpot;
import it.polimi.parkingService.webApplication.parking.models.Booking;
import it.polimi.parkingService.webApplication.parking.models.Parking;
import it.polimi.parkingService.webApplication.parking.models.ParkingArea;
import it.polimi.parkingService.webApplication.parking.models.ParkingSpot;
import it.polimi.parkingService.webApplication.parking.strategy.LinearSearchParkingSpotResearchStrategy;
import it.polimi.parkingService.webApplication.parking.strategy.ParkingSpotSearchCriteria;
import it.polimi.parkingService.webApplication.parking.strategy.PriorityQueueParkingSpotResearch;
import it.polimi.parkingService.webApplication.parking.strategy.SearchCriteria;
import it.polimi.parkingService.webApplication.payment.models.PaymentReceipt;
import it.polimi.parkingService.webApplication.payment.models.PaymentSystem;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * The {@code ParkingSpotService} handles any parking spot related business logic
 */
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

    private final PaymentSystem paymentSystem;

    /**
     * Constructs the services
     * @param parkingSpotRepository the services handling parking spot business logic
     * @param parkingService the services handling parking business logic
     * @param bookingService the services handling booking business logic
     * @param userService the services handling account business logic
     * @param qrCodeService the services handling qrcode business logic
     * @param sseService the services handling server-sent business logic
     * @param parkingAreaService the services handling parking area business logic
     * @param paymentSystem the services handling payment system business logic
     */
    @Autowired
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

    /**
     * Retrieves spots bound with parking, if present
     * @return spots with any parking
     */
    public Map<ParkingSpot, Parking> getSpotsWithParkings() {
        List<ParkingSpot> parkingSpots = findAll();
        Map<ParkingSpot, Parking> pairs = new LinkedHashMap<>(parkingSpots.size());

        // create a set of reserved spot IDs for faster lookups
        Set<Long> reservedSpotIds = new HashSet<>();
        List<Booking> bookings = bookingService.findByDate(LocalDate.now());
        for (Booking reservation : bookings) {
            if (!reservation.getRedeemed()) {
                reservedSpotIds.add(reservation.getParkingSpot().getId());
            }
        }

        for (ParkingSpot spot : parkingSpots) {
            // update spot status if reserved
            if (reservedSpotIds.contains(spot.getId())) {
                spot.setStatus(ParkingSpotStatus.RESERVED);
            }

            // check if spot is busy
            if (spot.getStatus() == ParkingSpotStatus.BUSY) {
                Parking parking = parkingService.findActualInProgressParkingBySpot(spot);
                pairs.put(spot, parking);
            } else {
                pairs.put(spot, null);
            }
        }
        return pairs;
    }

    /**
     * Terminates parking
     * @param token the jwt authorizing exit
     * @return the payment receipt
     * @throws ParkingNotTerminated if exit time is not been registered
     */
    @Override
    public PaymentReceipt doCheckout(String token) throws ParkingNotTerminated {
        // check token and retrieve parking
        Parking parking = parkingService.findById(qrCodeService.getTokenPayload("parkingId", token));
        // register leaving time
        parking.setLeaving(LocalDateTime.now());

        // check if parking has been bound with a booking
        PaymentReceipt receipt;
        if(parking.getBooking() == null) {
            // pay for parking
            parking.pay(paymentSystem);
            receipt = parking.getPaymentReceipt();
        } else
        {
            // get booking receipt
            receipt = parking.getBooking().getPaymentReceipt();
        }

        // update parking
        parkingService.save(parking);

        // update spot
        ParkingSpot parkingSpot = parking.getSpot();
        parkingSpot.setStatus(ParkingSpotStatus.FREE);
        save(parkingSpot);

        // send event to emitters to update all user in real time
        sseService.sendEvent(new Event(parkingSpot.getId(), parkingSpot.getStatus()));
        return receipt;
    }

    /**
     * Starts a parking
     * @param checkinToken jwt token authorizing access
     * @return parking
     * @throws SearchStrategyUndefined if a search spot strategy has not been defined
     */
    @Transactional
    public Parking startParking (String checkinToken) throws SearchStrategyUndefined {
        // check token and retrieve authorized user
        long userId = qrCodeService.getTokenPayload("userId", checkinToken);
        User user = userService.findById(userId);
        if(user == null) {
            throw new ResourceNotFound("User with id " + userId + " not found");
        }

        ParkingSpot parkingSpot;
        Parking parking = new Parking(user);

        // check if user has booking
        Booking booking = bookingService.findByCustomerUserAndRedeemedFalseAndDate(user, LocalDate.now());
        // if user has a valid booking
        if(booking != null && !booking.getRedeemed()){
            // get booked spot
            parkingSpot = booking.getParkingSpot();
            // redeem booking
            booking.setRedeemed(true);
            // save changes
            bookingService.update(booking.getId(), booking.getRedeemed());
            // bind parking with booking
            parking.setBooking(booking);
        }
        else {
            // retrieve parking area
            ParkingArea parkingArea = parkingAreaService.findByName(parkingAreaName);
            // update spots with reservations
            parkingArea = bookingService.updateSpotsWithDailyBooking(parkingArea, LocalDate.now());
            // find suitable parking spot
            parkingSpot = findSpotWithStrategy(parkingArea, user);
        }
        // update spot status
        parkingSpot.setStatus(ParkingSpotStatus.BUSY);
        update(parkingSpot.getId(), parkingSpot.getStatus());
        // send event to emitters to update all user in real time
        sseService.sendEvent(new Event(parkingSpot.getId(), parkingSpot.getStatus()));

        // create parking and save
        parking.setSpot(parkingSpot);
        parkingService.save(parking);

        return parking;
    }

    /**
     * Find a parking spot for the user
     * @param parkingArea the parking area including spots
     * @param user the customer
     * @return spot found
     */
    private ParkingSpot findSpotWithStrategy(ParkingArea parkingArea, User user ) throws SearchStrategyUndefined {
        // define search criteria
        SearchCriteria userSearchCriteria = getUserCriteria(user);

        // set search strategy
        parkingArea.setParkingSpotResearchStrategy(new LinearSearchParkingSpotResearchStrategy());

        // find spot
        ParkingSpot parkingSpot = parkingArea.searchForParkingSpot(userSearchCriteria);

        // if reserved spots for user needing assistance are not available, search in normal spots using a different strategy
        if(parkingSpot == null && (user.getIsPregnant() || user.getIsDisabled())) {
            userSearchCriteria.setParkingStripes(ParkingStripes.WHITE);
            parkingArea.setParkingSpotResearchStrategy(new PriorityQueueParkingSpotResearch());
            parkingSpot = parkingArea.searchForParkingSpot(userSearchCriteria);
        }

        if(parkingSpot == null) {
            throw new UnavailableParkingSpot("All parking spots are temporarily reserved");
        }

        return parkingSpot;
    }

    /**
     * Defines search criteria based on user details
     * @param user the user
     * @return criteria
     */
    private SearchCriteria getUserCriteria(User user) {
        ParkingSpotSearchCriteria userSearchCriteria = new ParkingSpotSearchCriteria();
        // is user has both disability and pregnancy condition, set disability as priority
        if(user.getIsPregnant()) {
            userSearchCriteria.setParkingStripes(ParkingStripes.PINK);
        }
        if(user.getIsDisabled()) {
            userSearchCriteria.setParkingStripes(ParkingStripes.YELLOW);
        }
        return userSearchCriteria;
    }

    /**
     * Books a parking spot
     * @param username authenticated username
     * @param booking the booking
     * @return confirmed booking
     * @throws SearchStrategyUndefined if parking spot search strategy has not been set yet
     */
    @Override
    public Booking reserveParkingSpot(String username, Booking booking) throws SearchStrategyUndefined {
        // find user
        User user = userService.findByUserName(username);

        // check if a booking is already present
        int reservations = bookingService.countBookingByCustomerUserAndDate(user, booking.getDate());
        if(reservations > 0) {
            throw new RuntimeException(username + " has already got reservation for " + booking.getDate());
        }
        // Check if there are any parking progress
        Optional<Parking> parking = parkingService.findInProgressParkingsByUserId(user);
        if(parking.isPresent() && (parking.get().getArrival().toLocalDate().isEqual(booking.getDate()))) {
            throw new RuntimeException(username + " has a parking in progress for " + booking.getDate());
        }

        // update spots status
        ParkingArea parkingArea = parkingAreaService.findByName(parkingAreaName);
        parkingArea = bookingService.updateSpotsWithDailyBooking(parkingArea, LocalDate.now());
        // find free spot
        ParkingSpot spot = findSpotWithStrategy(parkingArea, user);

        // register booking
        booking.setParkingSpot(spot);
        booking.setCustomerUser(user);
        booking.pay(paymentSystem);
        bookingService.save(booking);
        return booking;
    }

    /**
     * Toggles spot availability
     * @param id spot id
     */
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
    public List<ParkingSpot> findAll() {
        return parkingSpotRepository.findAll();
    }

    @Override
    public ParkingSpot findById(long id) {
        ParkingSpot spot = parkingSpotRepository.findById(id);

        if (spot == null) {
            throw new RuntimeException("Did not find parking spot - " + id);
        }

        return spot;
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
}
