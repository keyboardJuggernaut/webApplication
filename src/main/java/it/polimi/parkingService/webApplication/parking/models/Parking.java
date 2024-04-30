package it.polimi.parkingService.webApplication.parking.models;

import it.polimi.parkingService.webApplication.account.CustomerAccount;
import it.polimi.parkingService.webApplication.parking.exceptions.MissingEstimatedTime;
import it.polimi.parkingService.webApplication.parking.exceptions.ParkingNotTerminated;
import it.polimi.parkingService.webApplication.parking.exceptions.ParkingSpotNotFoundYet;
import it.polimi.parkingService.webApplication.payment.exceptions.PaymentFailed;
import it.polimi.parkingService.webApplication.payment.models.PaymentReceipt;
import it.polimi.parkingService.webApplication.payment.models.PaymentSystem;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Parking {
    private LocalTime estimatedTime;
    private LocalDateTime arrival;
    private LocalDateTime leaving;
    private CustomerAccount customerAccount;
    private ParkingSpot spot;
    private PaymentSystem paymentSystem;
    private final static double hourlyFee = 1.5;

    public Parking(LocalTime estimatedTime, LocalDateTime arrival, PaymentSystem paymentSystem) {
        this.estimatedTime = estimatedTime;
        this.arrival = arrival;
        this.paymentSystem = paymentSystem;
    }
    public Parking( LocalDateTime arrival,  PaymentSystem paymentSystem) {
        this.arrival = arrival;
        this.paymentSystem = paymentSystem;

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
        long parkingTime = Duration.between(arrival, leaving).toHours();
        return parkingTime * hourlyFee;
    }

    public PaymentReceipt pay() throws ParkingNotTerminated, PaymentFailed {
        double amount = getParkingCharge();
        return paymentSystem.processPayment(customerAccount, amount);
    }

    public CustomerAccount getCustomerAccount() {
        return customerAccount;
    }

    public void setCustomerAccount(CustomerAccount customerAccount) {
        this.customerAccount = customerAccount;
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
}
