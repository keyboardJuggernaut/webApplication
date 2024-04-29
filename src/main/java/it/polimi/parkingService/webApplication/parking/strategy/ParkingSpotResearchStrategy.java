package it.polimi.parkingService.webApplication.parking.strategy;

import it.polimi.parkingService.webApplication.parking.models.ParkingSpot;

public abstract class ParkingSpotResearchStrategy {

    // pass parkingSpots data structure chosen
    public abstract ParkingSpot findSpot(ParkingSpot[][] parkingSpots, SearchCriteria criteria);
}
