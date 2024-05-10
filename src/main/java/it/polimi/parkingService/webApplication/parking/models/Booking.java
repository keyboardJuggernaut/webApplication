package it.polimi.parkingService.webApplication.parking.models;

import it.polimi.parkingService.webApplication.account.models.User;
import it.polimi.parkingService.webApplication.payment.exceptions.PaymentFailed;
import it.polimi.parkingService.webApplication.payment.exceptions.RefundFailed;
import it.polimi.parkingService.webApplication.payment.models.PaymentReceipt;
import it.polimi.parkingService.webApplication.payment.models.PaymentSystem;
import it.polimi.parkingService.webApplication.utils.BaseEntity;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * The {@code Booking} represents booking model
 */
@Entity
@Table(name="booking")
public class Booking extends BaseEntity {

    @Column(name="date")
    private LocalDate date;

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

    /**
     * Amount to pay for a booking (daily)
     */
    public final static double DAILY_CHARGE = 15;

    /**
     * Minimum remaining days to booking date for refund check
     */
    public final static long DAYS_TO_REFUND = 1;


    public Booking(LocalDate date) {
        this.date = date;
    }

    public Booking(){}


    /**
     * Pays booking
     * @param paymentSystem the payment system
     * @throws PaymentFailed when payment fails
     */
    public void pay(PaymentSystem paymentSystem) throws PaymentFailed {
        paymentReceipt = paymentSystem.processPayment(customerUser, DAILY_CHARGE);
    }

    /**
     * Refunds the customer if is eligible
     * @param paymentSystem the payment system
     * @param adminAction true if action was throwed by admin
     * @throws RefundFailed if payment fails
     */
    public void refundCheck(PaymentSystem paymentSystem, Boolean adminAction) throws RefundFailed {
        // check booking has not already been claimed
        if(!claimed) {
            // if admin action, do not check temporal criteria for eligibility
            if (!adminAction) {
                // refund when the request arrives at least a certain time before booking date
                long remainingDays = ChronoUnit.DAYS.between(date, LocalDate.now());
                if (remainingDays > DAYS_TO_REFUND) {
                    // refund
                    paymentSystem.undoPayment(customerUser, paymentReceipt);
                }
            } else {
                paymentSystem.undoPayment(customerUser, paymentReceipt);
            }
        }
    }
    public LocalDate getDate() {
        return date;
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
