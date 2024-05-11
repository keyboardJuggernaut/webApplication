package it.polimi.parkingService.webApplication.parking.services;

import it.polimi.parkingService.webApplication.account.models.User;
import it.polimi.parkingService.webApplication.parking.dao.BookingRepository;
import it.polimi.parkingService.webApplication.parking.enums.ParkingSpotStatus;
import it.polimi.parkingService.webApplication.parking.exceptions.ResourceNotFound;
import it.polimi.parkingService.webApplication.parking.models.Booking;
import it.polimi.parkingService.webApplication.parking.models.ParkingArea;
import it.polimi.parkingService.webApplication.parking.models.ParkingSpot;
import it.polimi.parkingService.webApplication.payment.models.PaymentSystem;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * The {@code BookingService} handles any booking related business logic
 */
@Service
public class BookingService implements IBookingService {

    private final BookingRepository bookingRepository;

    private final PaymentSystem paymentSystem;


    /**
     * Constructs the service
     * @param bookingRepository the repository handling booking persistence logic
     * @param paymentSystem the service handling payment business logic
     */
    @Autowired
    public BookingService(BookingRepository bookingRepository, PaymentSystem paymentSystem) {
        this.bookingRepository = bookingRepository;
        this.paymentSystem = paymentSystem;
    }

    /**
     * Cancels a booking, checking for refund eligibility
     * @param id the booking id
     * @throws ResourceNotFound when booking is not present
     */
    @Override
    public void cancelBooking(long id) throws ResourceNotFound {
        Booking booking = findById(id);
        booking.refundCheck(paymentSystem, true);
        deleteById(id);
    }

    /**
     * Updates spots status if there are bookings in a certain date
     * @param parkingArea parking area including spots
     * @param date the date to inspect
     * @return updated spots
     */
    public ParkingArea updateSpotsWithDailyBooking(ParkingArea parkingArea, LocalDate date) {
        // find daily bookings
        List<Booking> bookings = findByDate(date);

        // if there are bookings
        if(!bookings.isEmpty()) {

            // Note: hash set data structure was chosen to do search operation with constant time complexity
            Set<Long> reservedSpotIds = new HashSet<>();

            // get booked spot ids
            for (Booking reservation : bookings) {
                reservedSpotIds.add(reservation.getParkingSpot().getId());
            }

            // update spot status if is booked
            for (ParkingSpot spot : parkingArea.getParkingSpots()) {
                if (reservedSpotIds.contains(spot.getId())) {
                    spot.setStatus(ParkingSpotStatus.RESERVED);
                }
            }
        }
        // no bookings
        return parkingArea;
    }

    @Override
    public Booking findById(long id) {
        Optional<Booking> result = bookingRepository.findById(id);

        if(result.isEmpty()) {
            throw new RuntimeException("Did not find booking id - " + id);
        }

        return result.get();
    }

    @Override
    public List<Booking> findByDate(LocalDate date) {
        return bookingRepository.findByDate(date);
    }

    @Override
    public int countBookingByCustomerUserAndDate(User customerUser, LocalDate date) {
        return bookingRepository.countBookingByCustomerUserAndDate(customerUser, date);
    }

    @Override
    public Booking save(Booking booking) {
        return bookingRepository.save(booking);
    }

    @Override
    public Booking findByCustomerUserAndRedeemedFalseAndDate(User customerUser, LocalDate date) {
        return bookingRepository.findByCustomerUserAndRedeemedFalseAndDate(customerUser, date);
    }

    @Override
    @Transactional
    public void update(long id, boolean redeemed) {
        bookingRepository.update(id, redeemed);
    }

    @Override
    public Integer countBookingByDate(LocalDate actualDate) {
        return bookingRepository.countBookingByDate(actualDate);
    }

    @Override
    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    @Override
    public void deleteById(long id) {
        bookingRepository.deleteById(id);
    }


}
