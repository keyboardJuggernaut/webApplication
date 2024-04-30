package it.polimi.parkingService.webApplication.parking.exceptions;

public class ParkingSpotNotFoundYet extends Exception{
    public ParkingSpotNotFoundYet(String message) {
        super(message);
    }
}
