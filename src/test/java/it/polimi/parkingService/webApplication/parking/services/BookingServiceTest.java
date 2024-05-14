package it.polimi.parkingService.webApplication.parking.services;

import it.polimi.parkingService.webApplication.account.models.User;
import it.polimi.parkingService.webApplication.parking.dao.BookingRepository;
import it.polimi.parkingService.webApplication.parking.enums.ParkingSpotStatus;
import it.polimi.parkingService.webApplication.parking.exceptions.ResourceNotFound;
import it.polimi.parkingService.webApplication.parking.models.Booking;
import it.polimi.parkingService.webApplication.parking.models.ParkingArea;
import it.polimi.parkingService.webApplication.parking.models.ParkingSpot;
import it.polimi.parkingService.webApplication.payment.models.PaymentSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BookingServiceTest {
    @InjectMocks
    private BookingService bookingService;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private PaymentSystem paymentSystem;

    private Booking booking;

    @BeforeEach
    public void setUp() {
        booking = new Booking();
        booking.setId(1L);
    }

    @Test
    public void testCancelBooking() throws ResourceNotFound {
        Booking booking = new Booking();
        booking.setRedeemed(false);
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));

        bookingService.cancelBooking(1L);

        verify(bookingRepository).deleteById(1L);
    }

    @Test
    public void testUpdateSpotsWithDailyBooking() {
        ParkingArea parkingArea = new ParkingArea();
        parkingArea.setParkingSpots(new ArrayList<ParkingSpot>(5));
        LocalDate date = LocalDate.now();
        List<Booking> bookings = new ArrayList<>();
        booking.setParkingSpot(new ParkingSpot());
        bookings.add(booking);

        when(bookingRepository.findByDate(date)).thenReturn(bookings);

        ParkingArea updatedParkingArea = bookingService.updateSpotsWithDailyBooking(parkingArea, date);

        for (ParkingSpot spot : updatedParkingArea.getParkingSpots()) {
            if (Objects.equals(spot.getId(), booking.getParkingSpot().getId())) {
                assertEquals(ParkingSpotStatus.RESERVED, spot.getStatus());
            } else {
                assertNotEquals(ParkingSpotStatus.RESERVED, spot.getStatus());
            }
        }
    }

    @Test
    public void testFindById() {
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));

        Booking foundBooking = bookingService.findById(1L);

        assertEquals(booking, foundBooking);
    }

    @Test
    public void testFindByDate() {
        List<Booking> bookings = new ArrayList<>();
        bookings.add(booking);

        when(bookingRepository.findByDate(LocalDate.now())).thenReturn(bookings);

        List<Booking> foundBookings = bookingService.findByDate(LocalDate.now());

        assertEquals(bookings, foundBookings);
    }

    @Test
    public void testCountBookingByCustomerUserAndDate() {
        User customerUser = new User();
        LocalDate date = LocalDate.now();

        when(bookingRepository.countBookingByCustomerUserAndDate(customerUser, date)).thenReturn(1);

        int count = bookingService.countBookingByCustomerUserAndDate(customerUser, date);

        assertEquals(1, count);
    }

    @Test
    public void testSave() {
        when(bookingRepository.save(booking)).thenReturn(booking);

        Booking savedBooking = bookingService.save(booking);

        assertEquals(booking, savedBooking);
    }

    @Test
    public void testFindByCustomerUserAndRedeemedFalseAndDate() {
        User customerUser = new User();
        LocalDate date = LocalDate.now();

        when(bookingRepository.findByCustomerUserAndRedeemedFalseAndDate(customerUser, date)).thenReturn(booking);

        Booking foundBooking = bookingService.findByCustomerUserAndRedeemedFalseAndDate(customerUser, date);

        assertEquals(booking, foundBooking);
    }

    @Test
    public void testUpdate() {
        long id = 1L;

        bookingService.update(id, true);

        verify(bookingRepository).update(id, true);
    }

    @Test
    public void testCountBookingByDate() {
        LocalDate actualDate = LocalDate.now();

        when(bookingRepository.countBookingByDate(actualDate)).thenReturn(1);

        int count = bookingService.countBookingByDate(actualDate);

        assertEquals(1, count);
    }
    @Test
    public void testFindAll() {
        List<Booking> bookings = new ArrayList<>();
        bookings.add(booking);

        when(bookingRepository.findAll()).thenReturn(bookings);

        List<Booking> foundBookings = bookingService.findAll();

        assertEquals(bookings, foundBookings);
    }

    @Test
    public void testDeleteById() {
        long id = 1L;

        bookingService.deleteById(id);

        verify(bookingRepository).deleteById(id);
    }
}
