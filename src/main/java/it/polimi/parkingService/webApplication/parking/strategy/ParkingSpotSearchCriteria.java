package it.polimi.parkingService.webApplication.parking.strategy;

import it.polimi.parkingService.webApplication.parking.enums.ParkingSpotStatus;
import it.polimi.parkingService.webApplication.parking.enums.StripeColor;
import it.polimi.parkingService.webApplication.parking.models.ParkingSpot;

public class ParkingSpotSearchCriteria implements SearchCriteria {
    private  ParkingSpotStatus status;
    private  StripeColor stripeColor;

    public ParkingSpotSearchCriteria() {
        this.status = ParkingSpotStatus.FREE;
        this.stripeColor = StripeColor.WHITE;
    }

    public ParkingSpotSearchCriteria( ParkingSpotStatus status, StripeColor stripeColor) {
        this.status = status;
        this.stripeColor = stripeColor;
    }

    @Override
    public boolean meetCriteria(ParkingSpot parkingSpot) {
        return status == parkingSpot.getStatus() && stripeColor == parkingSpot.getStripeColor();
    }

    public ParkingSpotStatus getStatus() {
        return status;
    }

    public StripeColor getStripeColor() {
        return stripeColor;
    }

    public void setStatus(ParkingSpotStatus status) {
        this.status = status;
    }

    public void setStripeColor(StripeColor stripeColor) {
        this.stripeColor = stripeColor;
    }
}
