package it.polimi.parkingService.webApplication.parking.models;

import it.polimi.parkingService.webApplication.account.models.User;
import it.polimi.parkingService.webApplication.parking.exceptions.MissingEstimatedTime;
import it.polimi.parkingService.webApplication.parking.exceptions.ParkingNotTerminated;
import it.polimi.parkingService.webApplication.parking.exceptions.ParkingSpotNotFoundYet;
import it.polimi.parkingService.webApplication.payment.exceptions.PaymentFailed;
import it.polimi.parkingService.webApplication.payment.models.PaymentReceipt;
import it.polimi.parkingService.webApplication.payment.models.PaymentSystem;
import it.polimi.parkingService.webApplication.utils.BaseEntity;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

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

    private final static double hourlyFee = 0.025;

    public Parking(User customerUser) {
        this.arrival = LocalDateTime.now();
        this.customerUser = customerUser;

    }
    public Parking(){
        this.arrival = LocalDateTime.now();
    }

    public LocalTime getEstimatedLeavingHour() throws MissingEstimatedTime, ParkingSpotNotFoundYet{
        if(spot == null) {
            throw new ParkingSpotNotFoundYet("Parking spot not found yet");
        }
        if(estimatedTime == null) {
            throw new MissingEstimatedTime("Missing estimated time for " + spot.getSpotIdentifier() + "parking spot");
        }

        return arrival.toLocalTime().plusHours(estimatedTime.getHour()).plusMinutes(estimatedTime.getMinute());
    }

    private double getParkingCharge() throws ParkingNotTerminated {
        if(leaving == null) {
            throw new ParkingNotTerminated("Parking still in progress");
        }
        long parkingTime = Duration.between(arrival, leaving).toMinutes();
        return parkingTime * hourlyFee;
    }

    public void pay(PaymentSystem paymentSystem) throws ParkingNotTerminated, PaymentFailed {
        double amount = getParkingCharge();
        paymentReceipt = paymentSystem.processPayment(customerUser, amount);
    }

    public User getCustomerUser() {
        return customerUser;
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

    public void setArrival(LocalDateTime arrival) {
        this.arrival = arrival;
    }

    public LocalDateTime getLeaving() {
        return leaving;
    }

    public void setLeaving(LocalDateTime leaving) {
        this.leaving = leaving;
    }

    public PaymentReceipt getPaymentReceipt() {
        return paymentReceipt;
    }

    public void setPaymentReceipt(PaymentReceipt paymentReceipt) {
        this.paymentReceipt = paymentReceipt;
    }

}
