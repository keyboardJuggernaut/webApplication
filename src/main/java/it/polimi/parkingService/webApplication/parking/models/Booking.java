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

@Entity
@Table(name="booking")
public class Booking extends BaseEntity {

    @Column(name="date")
    private LocalDate date;
    public final static double DAILY_CHARGE = 15;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="account_id")
    private User customerUser;

    @Transient
    private PaymentSystem paymentSystem;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_receipt_id")
    private PaymentReceipt paymentReceipt;

    public final static long HOURS_TO_REFUND = 48;


    public Booking(LocalDate date, PaymentSystem paymentSystem) {
        this.date = date;
        this.paymentSystem = paymentSystem;
    }

    public Booking(){}

    public LocalDate getDate() {
        return date;
    }

    public PaymentReceipt pay() throws PaymentFailed {
        PaymentReceipt receipt = paymentSystem.processPayment(customerUser, DAILY_CHARGE);
        setPaymentReceipt(receipt);
        return receipt;
    }

    public void refund() throws RefundFailed {
        long remainingHours = Duration.between(date, LocalDate.now()).toHours();
        if(remainingHours < HOURS_TO_REFUND) {
            paymentSystem.undoPayment(customerUser, paymentReceipt);
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

    public PaymentSystem getPaymentSystem() {
        return paymentSystem;
    }

    public void setPaymentSystem(PaymentSystem paymentSystem) {
        this.paymentSystem = paymentSystem;
    }

    public PaymentReceipt getPaymentReceipt() {
        return paymentReceipt;
    }

    public void setPaymentReceipt(PaymentReceipt paymentReceipt) {
        this.paymentReceipt = paymentReceipt;
    }
}
