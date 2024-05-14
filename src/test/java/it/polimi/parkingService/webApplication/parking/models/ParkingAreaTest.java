package it.polimi.parkingService.webApplication.parking.models;

import it.polimi.parkingService.webApplication.parking.enums.ParkingSpotStatus;
import it.polimi.parkingService.webApplication.parking.enums.ParkingStripes;
import it.polimi.parkingService.webApplication.parking.exceptions.SearchStrategyUndefined;
import it.polimi.parkingService.webApplication.parking.strategy.ParkingSpotResearchStrategy;
import it.polimi.parkingService.webApplication.parking.strategy.SearchCriteria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ParkingAreaTest {
    private ParkingArea parkingArea;
    private ParkingSpotResearchStrategy mockStrategy;
    private SearchCriteria mockCriteria;

    @BeforeEach
    public void setUp() {
        parkingArea = new ParkingArea("Main Parking", 2);
        mockStrategy = mock(ParkingSpotResearchStrategy.class);
        mockCriteria = mock(SearchCriteria.class);

        ParkingSpot spot1 = new ParkingSpot(0, 0);
        ParkingSpot spot2 = new ParkingSpot(0, 1);
        ParkingSpot spot3 = new ParkingSpot(1, 0);
        ParkingSpot spot4 = new ParkingSpot(1, 1);

        parkingArea.setParkingSpot(spot1);
        parkingArea.setParkingSpot(spot2);
        parkingArea.setParkingSpot(spot3);
        parkingArea.setParkingSpot(spot4);
    }

    @Test
    public void testSetParkingSpot() {
        ParkingSpot spot = new ParkingSpot(1, 1);
        parkingArea.setParkingSpot(spot);
        assertEquals(spot, parkingArea.getParkingSpot(1, 1));
    }

    @Test
    public void testSetParkingSpot_IndexOutOfBoundsException() {
        ParkingSpot spot = new ParkingSpot(2, 2);
        assertThrows(IndexOutOfBoundsException.class, () -> parkingArea.setParkingSpot(spot));
    }

    @Test
    public void testGetParkingSpot() {
        ParkingSpot spot = parkingArea.getParkingSpot(0, 0);
        assertEquals(ParkingSpotStatus.FREE, spot.getStatus());
        assertEquals(ParkingStripes.WHITE, spot.getStripeColor());
    }

    @Test
    public void testGetParkingSpot_IndexOutOfBoundsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> parkingArea.getParkingSpot(2, 2));
    }

    @Test
    public void testSearchForParkingSpot_Success() throws SearchStrategyUndefined {
        parkingArea.setParkingSpotResearchStrategy(mockStrategy);
        ParkingSpot expectedSpot = new ParkingSpot(0, 0);
        when(mockStrategy.findSpot(any(ParkingSpot[][].class), eq(mockCriteria))).thenReturn(expectedSpot);

        ParkingSpot result = parkingArea.searchForParkingSpot(mockCriteria);
        assertEquals(expectedSpot, result);
    }

    @Test
    public void testSearchForParkingSpot_StrategyUndefined() {
        assertThrows(SearchStrategyUndefined.class, () -> parkingArea.searchForParkingSpot(mockCriteria));
    }

    @Test
    public void testConvertToMatrix_ArraySizeMismatch() {
        List<ParkingSpot> mismatchedList = new ArrayList<>();
        mismatchedList.add(new ParkingSpot(0, 0));
        parkingArea.setParkingSpots(mismatchedList);

        assertThrows(IllegalArgumentException.class, parkingArea::convertToMatrix);
    }
}
