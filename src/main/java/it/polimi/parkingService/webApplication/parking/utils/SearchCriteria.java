package it.polimi.parkingService.webApplication.parking.utils;

import it.polimi.parkingService.webApplication.parking.models.ParkingSpot;

public interface SearchCriteria {

    boolean meetCriteria(ParkingSpot parkingSpot);
}
