package it.polimi.parkingService.webApplication.parking.strategy;

import it.polimi.parkingService.webApplication.parking.utils.SearchCriteria;
import it.polimi.parkingService.webApplication.parking.models.ParkingSpot;

public class LinearSearchParkingSpotResearchStrategy extends ParkingSpotResearchStrategy {

    @Override
    public ParkingSpot findSpot(ParkingSpot[][] parkingSpots, SearchCriteria criteria) {
        int order = parkingSpots.length;
        for (ParkingSpot[] parkingSpot : parkingSpots) {
            for (int j = 0; j < order; j++) {
                if (criteria.meetCriteria(parkingSpot[j])) {
                    return parkingSpot[j];
                }
            }
        }
        return null;
    }
}
