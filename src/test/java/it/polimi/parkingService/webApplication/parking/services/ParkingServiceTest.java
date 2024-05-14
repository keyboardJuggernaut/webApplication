package it.polimi.parkingService.webApplication.parking.services;

import com.google.zxing.WriterException;
import it.polimi.parkingService.webApplication.account.models.User;
import it.polimi.parkingService.webApplication.account.services.IUserService;
import it.polimi.parkingService.webApplication.parking.dao.ParkingRepository;
import it.polimi.parkingService.webApplication.parking.exceptions.ParkingAlreadyInProgress;
import it.polimi.parkingService.webApplication.parking.exceptions.ParkingNotInProgress;
import it.polimi.parkingService.webApplication.parking.models.Parking;
import it.polimi.parkingService.webApplication.parking.models.ParkingSpot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ParkingServiceTest {

    @InjectMocks
    private ParkingService parkingService;

    @Mock
    private ParkingRepository parkingRepository;

    @Mock
    private IUserService userService;

    @Mock
    private IQRCodeService qrCodeService;

    private User user;
    private Parking parking;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(1L);
        user.setUserName("testUsername");

        parking = new Parking();
        parking.setId(1L);
    }

    @Test
    public void testGetCheckInQRCode() throws ParkingAlreadyInProgress, IOException, WriterException {
        when(userService.findByUserName("testUsername")).thenReturn(user);
        when(parkingRepository.findInProgressParkingsByUserId(user)).thenReturn(Optional.empty());
        when(qrCodeService.getQRCodeWithEmbeddedToken("userId", user.getId())).thenReturn("testQRCode");

        String qrCode = parkingService.getCheckInQRCode("testUsername");

        assertEquals("testQRCode", qrCode);
    }

    @Test
    public void testGetCheckOutQRCode() throws ParkingAlreadyInProgress, IOException, WriterException, ParkingNotInProgress {
        when(userService.findByUserName("testUsername")).thenReturn(user);
        when(parkingRepository.findInProgressParkingsByUserId(user)).thenReturn(Optional.of(parking));
        when(qrCodeService.getQRCodeWithEmbeddedToken("parkingId", parking.getId())).thenReturn("testQRCode");

        String qrCode = parkingService.getCheckOutQRCode("testUsername");

        assertEquals("testQRCode", qrCode);
    }

    @Test
    public void testFindAll() {
        List<Parking> parkings = new ArrayList<>();
        parkings.add(parking);

        when(parkingRepository.findAll()).thenReturn(parkings);

        List<Parking> foundParkings = parkingService.findAll();

        assertEquals(parkings, foundParkings);
    }

    @Test
    public void testFindById() {
        when(parkingRepository.findById(1L)).thenReturn(Optional.of(parking));

        Parking foundParking = parkingService.findById(1L);

        assertEquals(parking, foundParking);
    }

    @Test
    public void testSave() {
        when(parkingRepository.save(parking)).thenReturn(parking);

        Parking savedParking = parkingService.save(parking);

        assertEquals(parking, savedParking);
    }

    @Test
    public void testDeleteById() {
        long id = 1L;

        parkingService.deleteById(id);

        verify(parkingRepository).deleteById(id);
    }

    @Test
    public void testFindInProgressParkingsByUserId() {
        when(parkingRepository.findInProgressParkingsByUserId(user)).thenReturn(Optional.of(parking));

        Optional<Parking> foundParking = parkingService.findInProgressParkingsByUserId(user);

        assertTrue(foundParking.isPresent());
        assertEquals(parking, foundParking.get());
    }

    @Test
    public void testFindActualInProgressParkingBySpot() {
        ParkingSpot spot = new ParkingSpot();
        spot.setId(1L);

        when(parkingRepository.findActualInProgressParkingBySpot(spot)).thenReturn(parking);

        Parking foundParking = parkingService.findActualInProgressParkingBySpot(spot);

        assertEquals(parking, foundParking);
    }

    @Test
    public void testUpdate() {
        long id = 1L;
        LocalTime estimatedTime = LocalTime.now();

        parkingService.update(id, estimatedTime);

        verify(parkingRepository).update(id, estimatedTime);
    }
}
