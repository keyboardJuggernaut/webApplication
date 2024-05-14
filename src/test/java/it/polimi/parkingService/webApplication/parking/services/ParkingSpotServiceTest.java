package it.polimi.parkingService.webApplication.parking.services;

import it.polimi.parkingService.webApplication.account.models.User;
import it.polimi.parkingService.webApplication.account.services.IUserService;
import it.polimi.parkingService.webApplication.parking.dao.ParkingSpotRepository;
import it.polimi.parkingService.webApplication.parking.enums.ParkingSpotStatus;
import it.polimi.parkingService.webApplication.parking.exceptions.ParkingNotTerminated;
import it.polimi.parkingService.webApplication.parking.exceptions.ResourceNotFound;
import it.polimi.parkingService.webApplication.parking.models.Booking;
import it.polimi.parkingService.webApplication.parking.models.Parking;
import it.polimi.parkingService.webApplication.parking.models.ParkingSpot;
import it.polimi.parkingService.webApplication.payment.models.PaymentReceipt;
import it.polimi.parkingService.webApplication.payment.models.PaymentSystem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ParkingSpotServiceTest {
    @InjectMocks
    private ParkingSpotService parkingSpotService;

    @Mock
    private ParkingSpotRepository parkingSpotRepository;

    @Mock
    private IParkingService parkingService;

    @Mock
    private IBookingService bookingService;

    @Mock
    private IUserService userService;

    @Mock
    private IQRCodeService qrCodeService;

    @Mock
    private SseService sseService;

    @Mock
    private IParkingAreaService parkingAreaService;

    @Mock
    private PaymentSystem paymentSystem;

    @Value("${parking.area.name}")
    private String parkingAreaName;

    @Test
    public void testGetSpotsWithParkings() {
        ParkingSpot spot1 = new ParkingSpot();
        spot1.setId(1L);
        ParkingSpot spot2 = new ParkingSpot();
        spot2.setId(2L);

        List<ParkingSpot> parkingSpots = new ArrayList<>();
        parkingSpots.add(spot1);
        parkingSpots.add(spot2);
        when(parkingSpotService.findAll()).thenReturn(parkingSpots);

        Booking booking1 = new Booking();
        booking1.setId(1L);
        booking1.setParkingSpot(spot1);
        booking1.setRedeemed(false);

        List<Booking> bookings = new ArrayList<>();
        bookings.add(booking1);

        when(bookingService.findByDate(LocalDate.now())).thenReturn(bookings);

        Map<ParkingSpot, Parking> expectedPairs = new HashMap<>();
        expectedPairs.put(spot1, null);
        expectedPairs.put(spot2, null);

        Map<ParkingSpot, Parking> actualPairs = parkingSpotService.getSpotsWithParkings();

        assertEquals(expectedPairs, actualPairs);
    }

    @Test
    public void testDoCheckout() throws ParkingNotTerminated {
        String token = "testToken";
        Parking parking = new Parking();
        ParkingSpot parkingSpot = new ParkingSpot();
        parkingSpot.setId(1L);
        parkingSpot.setStatus(ParkingSpotStatus.BUSY);
        parking.setSpot(parkingSpot);
        parking.setLeaving(LocalDateTime.now());

        parking.setBooking(new Booking());

        Parking savedParking = new Parking();
        savedParking.setSpot(parkingSpot);
        savedParking.setLeaving(LocalDateTime.now());
        savedParking.setCustomerUser(new User());

        when(parkingService.findById(anyLong())).thenReturn(parking);
        when(qrCodeService.getTokenPayload(eq("parkingId"), anyString())).thenReturn(1L);
        when(parkingService.save(any(Parking.class))).thenReturn(savedParking);
        when(paymentSystem.processPayment(new User(), 0.25)).thenReturn(new PaymentReceipt());

        PaymentReceipt receipt = parkingSpotService.doCheckout(token);

        assertNull(receipt);
        assertEquals(ParkingSpotStatus.FREE, parkingSpot.getStatus());
        verify(parkingService, times(1)).save(any(Parking.class));
        verify(sseService, times(1)).sendEvent(any(Event.class));
    }

    @Test
    public void startParking_UserNotFound_ThrowsException() {
        String checkinToken = "someToken";
        when(qrCodeService.getTokenPayload(eq("userId"), eq(checkinToken))).thenReturn(123L);
        when(userService.findById(123L)).thenReturn(null);

        assertThrows(ResourceNotFound.class, () -> parkingSpotService.startParking(checkinToken));
    }


    @Test
    public void reserveParkingSpot_UserAlreadyHasReservation_ThrowsException() {
        String username = "testUser";
        Booking booking = new Booking();
        User user = new User();
        when(userService.findByUserName(username)).thenReturn(user);
        when(bookingService.countBookingByCustomerUserAndDate(eq(user), any(LocalDate.class))).thenReturn(1);

        assertThrows(RuntimeException.class, () -> parkingSpotService.reserveParkingSpot(username, booking));
    }

    @Test
    public void toggleSpotAvailability_SpotUnavailable_SpotNowFree() {
        long spotId = 1L;
        ParkingSpot spot = new ParkingSpot();
        spot.setId(spotId);
        spot.setStatus(ParkingSpotStatus.UNAVAILABLE);

        when(parkingSpotRepository.findById(spotId)).thenReturn(spot);
        when(parkingSpotRepository.save(spot)).thenReturn(spot);

        parkingSpotService.toggleSpotAvailability(spotId);

        assertEquals(ParkingSpotStatus.FREE, spot.getStatus());
    }


    @Test
    public void findAll_ReturnsListOfParkingSpots() {
        List<ParkingSpot> expectedSpots = Arrays.asList(new ParkingSpot(), new ParkingSpot());
        when(parkingSpotRepository.findAll()).thenReturn(expectedSpots);

        List<ParkingSpot> actualSpots = parkingSpotService.findAll();

        assertEquals(expectedSpots.size(), actualSpots.size());
        assertTrue(actualSpots.containsAll(expectedSpots));
    }


    @Test
    public void findById_SpotExists_ReturnsSpot() {
        long spotId = 1L;
        ParkingSpot expectedSpot = new ParkingSpot();
        expectedSpot.setId(spotId);

        when(parkingSpotRepository.findById(spotId)).thenReturn(expectedSpot);

        ParkingSpot actualSpot = parkingSpotService.findById(spotId);

        assertEquals(expectedSpot, actualSpot);
    }

    @Test
    public void findById_SpotDoesNotExist_ThrowsException() {
        long spotId = 1L;
        when(parkingSpotRepository.findById(spotId)).thenReturn(null);

        assertThrows(RuntimeException.class, () -> parkingSpotService.findById(spotId));
    }

    @Test
    public void update_SpotStatusUpdatedSuccessfully() {
        long spotId = 1L;
        ParkingSpotStatus newStatus = ParkingSpotStatus.UNAVAILABLE;

        parkingSpotService.update(spotId, newStatus);

        verify(parkingSpotRepository, times(1)).update(spotId, newStatus);
    }
    @Test
    public void deleteById_CallsRepositoryMethodWithCorrectId() {
        long spotId = 1L;

        parkingSpotService.deleteById(spotId);

        verify(parkingSpotRepository, times(1)).deleteById(spotId);
    }

}
