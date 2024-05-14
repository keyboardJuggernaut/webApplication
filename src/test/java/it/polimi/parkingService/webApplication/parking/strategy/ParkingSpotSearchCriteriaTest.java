package it.polimi.parkingService.webApplication.parking.strategy;

import it.polimi.parkingService.webApplication.parking.enums.ParkingSpotStatus;
import it.polimi.parkingService.webApplication.parking.enums.ParkingStripes;
import it.polimi.parkingService.webApplication.parking.models.ParkingSpot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParkingSpotSearchCriteriaTest {
    private ParkingSpotSearchCriteria searchCriteria;
    private ParkingSpot matchingSpot;
    private ParkingSpot nonMatchingSpot;

    @BeforeEach
    public void setUp() {
        searchCriteria = new ParkingSpotSearchCriteria();
        matchingSpot = new ParkingSpot();
        matchingSpot.setStatus(ParkingSpotStatus.FREE);
        matchingSpot.setStripeColor(ParkingStripes.WHITE);

        nonMatchingSpot = new ParkingSpot();
        nonMatchingSpot.setStatus(ParkingSpotStatus.BUSY);
        nonMatchingSpot.setStripeColor(ParkingStripes.YELLOW);
    }

    @Test
    public void testMeetCriteria_MatchingSpot() {
        assertTrue(searchCriteria.meetCriteria(matchingSpot));
    }

    @Test
    public void testMeetCriteria_NonMatchingSpot() {
        assertFalse(searchCriteria.meetCriteria(nonMatchingSpot));
    }

    @Test
    public void testMeetCriteria_CustomStatus() {
        searchCriteria.setStatus(ParkingSpotStatus.BUSY);
        matchingSpot.setStatus(ParkingSpotStatus.BUSY);
        assertTrue(searchCriteria.meetCriteria(matchingSpot));
    }

    @Test
    public void testMeetCriteria_CustomStripeColor() {
        searchCriteria.setParkingStripes(ParkingStripes.YELLOW);
        matchingSpot.setStripeColor(ParkingStripes.YELLOW);
        assertTrue(searchCriteria.meetCriteria(matchingSpot));
    }

    @Test
    public void testMeetCriteria_CustomStatusAndStripeColor() {
        searchCriteria.setStatus(ParkingSpotStatus.BUSY);
        searchCriteria.setParkingStripes(ParkingStripes.YELLOW);
        matchingSpot.setStatus(ParkingSpotStatus.BUSY);
        matchingSpot.setStripeColor(ParkingStripes.YELLOW);
        assertTrue(searchCriteria.meetCriteria(matchingSpot));
    }
}
