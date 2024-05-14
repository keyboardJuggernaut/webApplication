package it.polimi.parkingService.webApplication.parking.models;

import it.polimi.parkingService.webApplication.parking.enums.ParkingSpotStatus;
import it.polimi.parkingService.webApplication.parking.enums.ParkingStripes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParkingSpotTest {
    private ParkingSpot parkingSpot;
    private ParkingArea parkingArea;

    @BeforeEach
    public void setUp() {
        parkingArea = new ParkingArea("Test Area", 2);
        parkingSpot = new ParkingSpot(0, 1);
    }

    @Test
    public void testGetSpotIdentifier() {
        assertEquals("01", parkingSpot.getSpotIdentifier());
    }

    @Test
    public void testGetAndSetStatus() {
        parkingSpot.setStatus(ParkingSpotStatus.BUSY);
        assertEquals(ParkingSpotStatus.BUSY, parkingSpot.getStatus());
    }

    @Test
    public void testGetAndSetStripeColor() {
        parkingSpot.setStripeColor(ParkingStripes.YELLOW);
        assertEquals(ParkingStripes.YELLOW, parkingSpot.getStripeColor());
    }

    @Test
    public void testGetAndSetParkingArea() {
        parkingSpot.setParkingArea(parkingArea);
        assertEquals(parkingArea, parkingSpot.getParkingArea());
    }

    @Test
    public void testToString() {
        parkingSpot.setParkingArea(parkingArea);
        parkingSpot.setStatus(ParkingSpotStatus.BUSY);
        parkingSpot.setStripeColor(ParkingStripes.YELLOW);

        String expectedString = "ParkingSpot{parking=" + parkingArea +
                ", status=BUSY" +
                ", stripeColor=YELLOW" +
                ", rowNumber=0" +
                ", columnNumber=1" +
                '}';
        assertEquals(expectedString, parkingSpot.toString());
    }
}
