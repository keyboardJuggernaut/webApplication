package it.polimi.parkingService.webApplication.payment.exceptions;

public class PaymentFailed extends RuntimeException {
    public PaymentFailed(String message) {
        super(message);
    }
}
