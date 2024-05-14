package it.polimi.parkingService.webApplication.parking.strategy;

import it.polimi.parkingService.webApplication.parking.enums.ParkingSpotStatus;
import it.polimi.parkingService.webApplication.parking.enums.ParkingStripes;
import it.polimi.parkingService.webApplication.parking.models.ParkingSpot;

/**
 * The {@code ParkingSpotSearchCriteria} represents user criteria model
 */
public class ParkingSpotSearchCriteria implements SearchCriteria {
    private  ParkingSpotStatus status;
    private ParkingStripes parkingStripes;

    public ParkingSpotSearchCriteria() {
        this.status = ParkingSpotStatus.FREE;
        this.parkingStripes = ParkingStripes.WHITE;
    }

    /**
     * Check if spot matches the user criteria
     * @param parkingSpot the parking spot to evaluate
     * @return the outcome of the check
     */
    @Override
    public boolean meetCriteria(ParkingSpot parkingSpot) {
        return status == parkingSpot.getStatus() && parkingStripes == parkingSpot.getStripeColor();
    }

    public ParkingSpotStatus getStatus() {
        return status;
    }

    public void setParkingStripes(ParkingStripes parkingStripes) {
        this.parkingStripes = parkingStripes;
    }

    public void setStatus(ParkingSpotStatus status) {
        this.status = status;
    }

}
