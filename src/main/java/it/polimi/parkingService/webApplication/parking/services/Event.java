package it.polimi.parkingService.webApplication.parking.services;

import it.polimi.parkingService.webApplication.parking.enums.ParkingSpotStatus;

public class Event {
    private Long id;
    private ParkingSpotStatus status;

    public Event(Long id, ParkingSpotStatus status) {
        this.id = id;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ParkingSpotStatus getStatus() {
        return status;
    }

    public void setStatus(ParkingSpotStatus status) {
        this.status = status;
    }
}
