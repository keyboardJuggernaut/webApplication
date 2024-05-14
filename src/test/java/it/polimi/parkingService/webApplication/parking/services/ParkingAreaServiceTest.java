package it.polimi.parkingService.webApplication.parking.services;

import it.polimi.parkingService.webApplication.parking.dao.ParkingAreaRepository;
import it.polimi.parkingService.webApplication.parking.models.ParkingArea;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ParkingAreaServiceTest {
    @InjectMocks
    private ParkingAreaService parkingAreaService;

    @Mock
    private ParkingAreaRepository parkingAreaRepository;

    private ParkingArea parkingArea;

    @BeforeEach
    public void setUp() {
        parkingArea = new ParkingArea();
        parkingArea.setId(1L);
    }

    @Test
    public void testFindByName() {
        String name = "TestParkingArea";

        when(parkingAreaRepository.findByName(name)).thenReturn(parkingArea);

        ParkingArea foundParkingArea = parkingAreaService.findByName(name);

        assertEquals(parkingArea, foundParkingArea);
    }

    @Test
    public void testFindAll() {
        List<ParkingArea> parkingAreas = new ArrayList<>();
        parkingAreas.add(parkingArea);

        when(parkingAreaRepository.findAll()).thenReturn(parkingAreas);

        List<ParkingArea> foundParkingAreas = parkingAreaService.findAll();

        assertEquals(parkingAreas, foundParkingAreas);
    }

    @Test
    public void testFindById() {
        when(parkingAreaRepository.findById(1L)).thenReturn(Optional.of(parkingArea));

        ParkingArea foundParkingArea = parkingAreaService.findById(1L);

        assertEquals(parkingArea, foundParkingArea);
    }

    @Test
    public void testSave() {
        when(parkingAreaRepository.save(parkingArea)).thenReturn(parkingArea);

        ParkingArea savedParkingArea = parkingAreaService.save(parkingArea);

        assertEquals(parkingArea, savedParkingArea);
    }

    @Test
    public void testDeleteById() {
        long id = 1L;

        parkingAreaService.deleteById(id);

        verify(parkingAreaRepository).deleteById(id);
    }
}
