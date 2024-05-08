package it.polimi.parkingService.webApplication.parking.models;

import it.polimi.parkingService.webApplication.account.models.User;
import it.polimi.parkingService.webApplication.payment.exceptions.PaymentFailed;
import it.polimi.parkingService.webApplication.payment.exceptions.RefundFailed;
import it.polimi.parkingService.webApplication.payment.models.PaymentReceipt;
import it.polimi.parkingService.webApplication.payment.models.PaymentSystem;
import it.polimi.parkingService.webApplication.utils.BaseEntity;
import jakarta.persistence.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name="booking")
public class Booking extends BaseEntity {

    @Column(name="date")
    private LocalDate date;
    public final static double DAILY_CHARGE = 15;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="account_id")
    private User customerUser;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_receipt_id")
    private PaymentReceipt paymentReceipt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parking_spot_id")
    private ParkingSpot parkingSpot;

    @Column(name="claimed")
    private Boolean claimed;

    public final static long DAYS_TO_REFUND = 1;


    public Booking(LocalDate date) {
        this.date = date;
    }

    public Booking(){}

    public LocalDate getDate() {
        return date;
    }

    public void pay(PaymentSystem paymentSystem) throws PaymentFailed {
        paymentReceipt = paymentSystem.processPayment(customerUser, DAILY_CHARGE);
    }

    public void refund(PaymentSystem paymentSystem, Boolean adminAction) throws RefundFailed {
        if(!claimed) {
            if (!adminAction) {
                long remainingDays = ChronoUnit.DAYS.between(date, LocalDate.now());
                if (remainingDays < DAYS_TO_REFUND) {
                    paymentSystem.undoPayment(customerUser, paymentReceipt);
                }
            } else {
                paymentSystem.undoPayment(customerUser, paymentReceipt);
            }
        }
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public User getCustomerUser() {
        return customerUser;
    }

    public void setCustomerUser(User customerUser) {
        this.customerUser = customerUser;
    }


    public PaymentReceipt getPaymentReceipt() {
        return paymentReceipt;
    }

    public void setPaymentReceipt(PaymentReceipt paymentReceipt) {
        this.paymentReceipt = paymentReceipt;
    }

    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }

    public void setParkingSpot(ParkingSpot parkingSpot) {
        this.parkingSpot = parkingSpot;
    }

    public Boolean getClaimed() {
        return claimed;
    }

    public void setClaimed(Boolean claimed) {
        this.claimed = claimed;
    }
}
