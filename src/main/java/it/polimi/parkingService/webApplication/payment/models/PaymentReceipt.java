package it.polimi.parkingService.webApplication.payment.models;

import it.polimi.parkingService.webApplication.account.models.Account;
import it.polimi.parkingService.webApplication.parking.models.Booking;
import it.polimi.parkingService.webApplication.utils.BaseEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="payment_receipt")
public class PaymentReceipt extends BaseEntity {

    @Column(name="timestamp")
    private LocalDateTime timestamp;
    @Column(name="amount")
    private double amount;

    @OneToOne(mappedBy = "paymentReceipt")
    private Booking booking;
    @Transient
    private Account customerAccount;

    public PaymentReceipt(LocalDateTime timestamp, double amount, Account customerAccount) {
        this.timestamp = timestamp;
        this.amount = amount;
        this.customerAccount = customerAccount;
    }

    public PaymentReceipt(){}

    public Account getCustomerAccount() {
        return customerAccount;
    }

    public void setCustomerAccount(Account customerAccount) {
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

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }
}
