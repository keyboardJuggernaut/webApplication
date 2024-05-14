package it.polimi.parkingService.webApplication.parking.strategy;

import it.polimi.parkingService.webApplication.parking.enums.ParkingSpotStatus;
import it.polimi.parkingService.webApplication.parking.enums.ParkingStripes;
import it.polimi.parkingService.webApplication.parking.models.ParkingSpot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class LinearSearchParkingSpotResearchStrategyTest {

    private LinearSearchParkingSpotResearchStrategy researchStrategy;
    private ParkingSpot[][] parkingSpots;
    private SearchCriteria criteria;

    @BeforeEach
    public void setUp() {
        researchStrategy = new LinearSearchParkingSpotResearchStrategy();
        criteria = new ParkingSpotSearchCriteria();

        parkingSpots = new ParkingSpot[2][2];

        parkingSpots[0][0] = new ParkingSpot(0, 0);
        parkingSpots[0][1] = new ParkingSpot(0, 1);
        parkingSpots[1][0] = new ParkingSpot(1, 0);
        parkingSpots[1][1] = new ParkingSpot(1, 1);
        parkingSpots[1][1].setStripeColor(ParkingStripes.YELLOW);
    }

    @Test
    public void testFindSpot_Found() {
        ParkingSpot result = researchStrategy.findSpot(parkingSpots, criteria);
        assertEquals(parkingSpots[0][0], result);
    }

    @Test
    public void testFindSpot_NotFound() {
        criteria.setStatus(ParkingSpotStatus.BUSY);
        criteria.setParkingStripes(ParkingStripes.WHITE);
        ParkingSpot result = researchStrategy.findSpot(parkingSpots, criteria);
        assertNull(result);
    }

    @Test
    public void testFindSpot_ClosestSpot() {
        parkingSpots[0][0] = new ParkingSpot(0, 0);
        parkingSpots[1][1] = new ParkingSpot(1, 1);

        ParkingSpot result = researchStrategy.findSpot(parkingSpots, criteria);
        assertEquals(parkingSpots[0][0], result);
    }

    @Test
    public void testFindSpot_CustomCriteria() {
        criteria.setStatus(ParkingSpotStatus.BUSY);
        criteria.setParkingStripes(ParkingStripes.YELLOW);
        ParkingSpot result = researchStrategy.findSpot(parkingSpots, criteria);
        assertNull(result);
    }
}
