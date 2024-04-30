package it.polimi.parkingService.webApplication.payment.models;

import java.time.LocalDateTime;

public class Payment {
    private boolean isSucceeded;

    public PaymentReceipt doPayment(double amount) {
        // TODO: simulate request for payment
        isSucceeded = true;
        return new PaymentReceipt(LocalDateTime.now(), amount);
    }

    public boolean isSucceded() {
        return isSucceeded;
    }

    public void setSucceded(boolean succeded) {
        isSucceeded = succeded;
    }
}
