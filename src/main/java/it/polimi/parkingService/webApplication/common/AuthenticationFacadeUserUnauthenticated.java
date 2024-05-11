package it.polimi.parkingService.webApplication.common;

public class AuthenticationFacadeUserUnauthenticated extends Exception{
    public AuthenticationFacadeUserUnauthenticated(String message) {
        super(message);
    }
}
