package it.polimi.parkingService.webApplication.parking.strategy;

import it.polimi.parkingService.webApplication.parking.models.ParkingSpot;

public interface SearchCriteria {

    /**
     * Define logic to meet criteria
     * @param parkingSpot the parking spot to evaluate
     * @return true if criteria are met
     */
    boolean meetCriteria(ParkingSpot parkingSpot);
}
