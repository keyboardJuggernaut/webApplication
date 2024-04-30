package it.polimi.parkingService.webApplication.payment.exceptions;

public class RefundFailed extends RuntimeException {
    public RefundFailed(String message) {
        super(message);
    }
}
