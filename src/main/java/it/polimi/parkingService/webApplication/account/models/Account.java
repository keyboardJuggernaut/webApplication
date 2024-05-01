package it.polimi.parkingService.webApplication.account.models;

import it.polimi.parkingService.webApplication.parking.models.Booking;
import it.polimi.parkingService.webApplication.parking.models.Parking;
import it.polimi.parkingService.webApplication.payment.models.PaymentMethod;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="account")
public class Account extends UserAccount{

    @Column(name="license_plate")
    private String licensePlate;
    @Column(name="is_disabled")
    private boolean isDisabled;
    @Column(name="is_pregnant")
    private boolean isPregnant;

    @OneToMany(
            mappedBy = "account",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    private List<Role> roles;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_method_id")
    private PaymentMethod paymentMethod;

    @OneToMany(
            mappedBy = "customerAccount",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    private List<Parking> parkings;

    @OneToMany(
            mappedBy = "customerAccount",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    private List<Booking> bookings;

    public Account(String username, String password, UserStatus status, String firstName, String lastName, String licensePlate, boolean isDisabled, boolean isPregnant, PaymentMethod paymentMethod) {
        super(username, password, status, firstName, lastName);
        this.licensePlate = licensePlate;
        this.isDisabled = isDisabled;
        this.isPregnant = isPregnant;
        this.paymentMethod = paymentMethod;
    }

    public Account() {}

    public void addRole(Role role){
        if(roles == null) {
            roles = new ArrayList<>();
        }
        roles.add(role);
        role.setAccount(this);
    }

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

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
