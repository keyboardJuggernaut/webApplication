package it.polimi.parkingService.webApplication.messaging.exceptions;

public class InvalidThreadStatus extends Exception{
    public InvalidThreadStatus(String message) {
        super(message);
    }
}
