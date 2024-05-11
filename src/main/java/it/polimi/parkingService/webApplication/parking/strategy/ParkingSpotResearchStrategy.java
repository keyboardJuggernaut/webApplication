package it.polimi.parkingService.webApplication.parking.strategy;

import it.polimi.parkingService.webApplication.parking.models.ParkingSpot;

public abstract class ParkingSpotResearchStrategy {

    // Note: default fixed spots data structure is matrix
    public abstract ParkingSpot findSpot(ParkingSpot[][] parkingSpots, SearchCriteria criteria);
}
