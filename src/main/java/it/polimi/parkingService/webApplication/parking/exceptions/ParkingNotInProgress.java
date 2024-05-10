package it.polimi.parkingService.webApplication.parking.exceptions;

public class ParkingNotInProgress extends RuntimeException{
    public ParkingNotInProgress(String message) {
        super(message);
    }
}
