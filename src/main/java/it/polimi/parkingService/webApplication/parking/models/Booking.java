package it.polimi.parkingService.webApplication.parking.models;

import it.polimi.parkingService.webApplication.account.CustomerAccount;
import it.polimi.parkingService.webApplication.parking.exceptions.ParkingNotTerminated;
import it.polimi.parkingService.webApplication.payment.exceptions.PaymentFailed;
import it.polimi.parkingService.webApplication.payment.exceptions.RefundFailed;
import it.polimi.parkingService.webApplication.payment.models.PaymentReceipt;
import it.polimi.parkingService.webApplication.payment.models.PaymentSystem;

import java.time.Duration;
import java.time.LocalDate;

public class Booking {
    private LocalDate date;
    public final static double DAILY_CHARGE = 15;

    private CustomerAccount customerAccount;

    private PaymentSystem paymentSystem;

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
        PaymentReceipt receipt = paymentSystem.processPayment(customerAccount, DAILY_CHARGE);
        setPaymentReceipt(receipt);
        return receipt;
    }

    public void refund() throws RefundFailed {
        long remainingHours = Duration.between(date, LocalDate.now()).toHours();
        if(remainingHours < HOURS_TO_REFUND) {
            paymentSystem.undoPayment(customerAccount, paymentReceipt);
        }
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public CustomerAccount getCustomerAccount() {
        return customerAccount;
    }

    public void setCustomerAccount(CustomerAccount customerAccount) {
        this.customerAccount = customerAccount;
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
