package it.polimi.parkingService.webApplication.parking.strategy;

import it.polimi.parkingService.webApplication.parking.enums.ParkingStripes;
import it.polimi.parkingService.webApplication.parking.models.ParkingSpot;

public interface SearchCriteria {

    /**
     * Defines the logic to meet criteria
     * @param parkingSpot the parking spot to evaluate
     * @return true if criteria are met
     */
    boolean meetCriteria(ParkingSpot parkingSpot);

    void setStripeColor(ParkingStripes parkingStripes);
}
