package it.polimi.parkingService.webApplication.parking.services;

import it.polimi.parkingService.webApplication.parking.enums.ParkingSpotStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventTest {
    @InjectMocks
    private Event event;

    private final Long id = 1L;
    private final ParkingSpotStatus status = ParkingSpotStatus.FREE;

    @BeforeEach
    public void setUp() {
        event = new Event(id, status);
    }

    @Test
    public void testGetId() {
        assertEquals(id, event.getId());
    }

    @Test
    public void testSetId() {
        Long newId = 2L;
        event.setId(newId);
        assertEquals(newId, event.getId());
    }

    @Test
    public void testGetStatus() {
        assertEquals(status, event.getStatus());
    }

    @Test
    public void testSetStatus() {
        ParkingSpotStatus newStatus = ParkingSpotStatus.RESERVED;
        event.setStatus(newStatus);
        assertEquals(newStatus, event.getStatus());
    }
}
