package it.polimi.parkingService.webApplication.parking.models;

import it.polimi.parkingService.webApplication.account.models.User;
import it.polimi.parkingService.webApplication.parking.exceptions.ParkingNotTerminated;
import it.polimi.parkingService.webApplication.payment.exceptions.PaymentFailed;
import it.polimi.parkingService.webApplication.payment.models.PaymentReceipt;
import it.polimi.parkingService.webApplication.payment.models.PaymentSystem;
import it.polimi.parkingService.webApplication.common.BaseEntity;
import jakarta.persistence.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * The {@code Parking} represents parking model
 */
@Entity
@Table(name="parking")
public class Parking extends BaseEntity {
    @Column(name="estimated_time")
    private LocalTime estimatedTime;
    @Column(name="arrival")
    private LocalDateTime arrival;

    @Column(name="leaving")
    private LocalDateTime leaving;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_receipt_id")
    private PaymentReceipt paymentReceipt;

    @ManyToOne( fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="account_id")
    private User customerUser;

    @ManyToOne(
            fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="parking_spot_id")
    private ParkingSpot spot;

    @OneToOne(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "booking_id")
    private Booking booking;

    /**
     * Hourly parking fee
     */
    public final static double FEE_PER_MINUTE = 0.025;

    public final static double MINIMUM_CHARGE = 0.5;

    public Parking(User customerUser) {
        this.arrival = LocalDateTime.now();
        this.customerUser = customerUser;

    }
    public Parking(){
        this.arrival = LocalDateTime.now();
    }

    /**
     * Computes parking charge based on elapsed time
     * @return parking charge amount
     * @throws ParkingNotTerminated if parking has not been checked out
     */
    private double getParkingCharge() throws ParkingNotTerminated {
        // check if check out has been done
        if(leaving == null) {
            throw new ParkingNotTerminated("Parking still in progress");
        }
        // compute duration
        long parkingTime = Duration.between(arrival, leaving).toMinutes();
        // compute charge
        if(parkingTime <= 0) {
            return MINIMUM_CHARGE;
        }
        return parkingTime * FEE_PER_MINUTE;
    }

    /**
     * Pays for the parking
     * @param paymentSystem the payment system
     * @throws ParkingNotTerminated if parking is still in progress
     * @throws PaymentFailed if payment fails
     */
    public void pay(PaymentSystem paymentSystem) throws ParkingNotTerminated, PaymentFailed {
        paymentReceipt = paymentSystem.processPayment(customerUser, getParkingCharge());
    }


    public void setCustomerUser(User account) {
        this.customerUser = account;
    }

    public ParkingSpot getSpot() {
        return spot;
    }

    public void setSpot(ParkingSpot spot) {
        this.spot = spot;
    }

    public LocalTime getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(LocalTime estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public LocalDateTime getArrival() {
        return arrival;
    }

    public void setLeaving(LocalDateTime leaving) {
        this.leaving = leaving;
    }

    public PaymentReceipt getPaymentReceipt() {
        return paymentReceipt;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }
}
