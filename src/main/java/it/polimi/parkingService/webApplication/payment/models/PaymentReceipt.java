package it.polimi.parkingService.webApplication.payment.models;

import it.polimi.parkingService.webApplication.account.CustomerAccount;

import java.time.LocalDateTime;

public class PaymentReceipt {
    private LocalDateTime timestamp;
    private double amount;

    private CustomerAccount customerAccount;

    public PaymentReceipt(LocalDateTime timestamp, double amount, CustomerAccount customerAccount) {
        this.timestamp = timestamp;
        this.amount = amount;
        this.customerAccount = customerAccount;
    }

    public CustomerAccount getCustomerAccount() {
        return customerAccount;
    }

    public void setCustomerAccount(CustomerAccount customerAccount) {
        this.customerAccount = customerAccount;
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
