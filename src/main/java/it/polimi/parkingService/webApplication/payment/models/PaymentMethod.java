package it.polimi.parkingService.webApplication.payment.models;

import java.time.LocalDate;

public class PaymentMethod {
    private String pan;
    private String cvv;

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
