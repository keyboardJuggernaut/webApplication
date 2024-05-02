package it.polimi.parkingService.webApplication.payment.models;

import it.polimi.parkingService.webApplication.account.models.User;
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
    private User customerUser;

    public PaymentReceipt(LocalDateTime timestamp, double amount, User customerUser) {
        this.timestamp = timestamp;
        this.amount = amount;
        this.customerUser = customerUser;
    }

    public PaymentReceipt(){}

    public User getCustomerUser() {
        return customerUser;
    }

    public void setCustomerUser(User customerUser) {
        this.customerUser = customerUser;
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
