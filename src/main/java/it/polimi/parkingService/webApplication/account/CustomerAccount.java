package it.polimi.parkingService.webApplication.account;

import it.polimi.parkingService.webApplication.parking.models.Booking;
import it.polimi.parkingService.webApplication.parking.models.Parking;
import it.polimi.parkingService.webApplication.payment.models.PaymentMethod;

import java.util.ArrayList;
import java.util.List;

public class CustomerAccount extends UserAccount{

    private String licensePlate;
    private boolean isDisabled;
    private boolean isPregnant;
    private PaymentMethod paymentMethod;

    private List<Parking> parkings;

    private List<Booking> bookings;

    public CustomerAccount(String username, String password, UserStatus status, String firstName, String lastName, String licensePlate, boolean isDisabled, boolean isPregnant, PaymentMethod paymentMethod) {
        super(username, password, status, firstName, lastName);
        this.licensePlate = licensePlate;
        this.isDisabled = isDisabled;
        this.isPregnant = isPregnant;
        this.paymentMethod = paymentMethod;
    }

    public CustomerAccount() {}

    public void addParking(Parking parking) {
        if(parkings == null) {
            parkings = new ArrayList<>();
        }
        parkings.add(parking);
        parking.setCustomerAccount(this);
    }

    public void addBooking(Booking booking) {
        if(bookings == null) {
            bookings = new ArrayList<>();
        }
        bookings.add(booking);
        booking.setCustomerAccount(this);
    }

    @Override
    public UserAccount getDetails() {
        return this;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public List<Parking> getParkings() {
        return parkings;
    }

    public void setParkings(List<Parking> parkings) {
        this.parkings = parkings;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public boolean isDisabled() {
        return isDisabled;
    }

    public void setDisabled(boolean disabled) {
        isDisabled = disabled;
    }

    public boolean isPregnant() {
        return isPregnant;
    }

    public void setPregnant(boolean pregnant) {
        isPregnant = pregnant;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
