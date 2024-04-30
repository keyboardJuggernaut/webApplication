package it.polimi.parkingService.webApplication.payment.models;

import java.time.LocalDateTime;

public class PaymentReceipt {
    private LocalDateTime timestamp;
    private double amount;

    public PaymentReceipt(LocalDateTime timestamp, double amount) {
        this.timestamp = timestamp;
        this.amount = amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
