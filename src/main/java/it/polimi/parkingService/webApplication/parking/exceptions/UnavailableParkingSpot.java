package it.polimi.parkingService.webApplication.parking.exceptions;

public class UnavailableParkingSpot extends RuntimeException{
    public UnavailableParkingSpot(String message) {
        super(message);
    }
}
