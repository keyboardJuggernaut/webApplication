package it.polimi.parkingService.webApplication.payment.models;

import it.polimi.parkingService.webApplication.utils.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name="payment_method")
public class PaymentMethod extends BaseEntity {

    @Column(name = "pan")
    private String pan;

    @Column(name = "cvv")
    private String cvv;
    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    public PaymentMethod(String pan, String cvv, LocalDate expirationDate) {
        this.pan = pan;
        this.cvv = cvv;
        this.expirationDate = expirationDate;
    }

    public PaymentMethod() {
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }
}
