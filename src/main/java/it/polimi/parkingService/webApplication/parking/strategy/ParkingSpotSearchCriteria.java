package it.polimi.parkingService.webApplication.parking.strategy;

import it.polimi.parkingService.webApplication.parking.enums.ParkingSpotStatus;
import it.polimi.parkingService.webApplication.parking.enums.ParkingStripes;
import it.polimi.parkingService.webApplication.parking.models.ParkingSpot;

public class ParkingSpotSearchCriteria implements SearchCriteria {
    private  ParkingSpotStatus status;
    private ParkingStripes parkingStripes;

    public ParkingSpotSearchCriteria() {
        this.status = ParkingSpotStatus.FREE;
        this.parkingStripes = ParkingStripes.WHITE;
    }

    public ParkingSpotSearchCriteria( ParkingSpotStatus status, ParkingStripes parkingStripes) {
        this.status = status;
        this.parkingStripes = parkingStripes;
    }

    @Override
    public boolean meetCriteria(ParkingSpot parkingSpot) {
        return status == parkingSpot.getStatus() && parkingStripes == parkingSpot.getStripeColor();
    }

    public ParkingSpotStatus getStatus() {
        return status;
    }

    public ParkingStripes getStripeColor() {
        return parkingStripes;
    }

    public void setStatus(ParkingSpotStatus status) {
        this.status = status;
    }

    public void setStripeColor(ParkingStripes parkingStripes) {
        this.parkingStripes = parkingStripes;
    }
}
