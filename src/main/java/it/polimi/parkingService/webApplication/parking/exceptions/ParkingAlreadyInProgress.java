package it.polimi.parkingService.webApplication.parking.exceptions;

public class ParkingAlreadyInProgress extends RuntimeException{
    public ParkingAlreadyInProgress(String message) {
        super(message);
    }
}
