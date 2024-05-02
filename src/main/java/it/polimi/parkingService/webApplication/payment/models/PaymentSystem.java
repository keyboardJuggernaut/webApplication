package it.polimi.parkingService.webApplication.payment.models;

import it.polimi.parkingService.webApplication.account.models.User;
import it.polimi.parkingService.webApplication.payment.exceptions.PaymentFailed;
import it.polimi.parkingService.webApplication.payment.exceptions.RefundFailed;

import java.time.LocalDateTime;

public class PaymentSystem {
    private boolean isSucceeded;

    public boolean isSucceded() {
        return isSucceeded;
    }

    public void setSucceded(boolean succeded) {
        isSucceeded = succeded;
    }

    public PaymentReceipt processPayment(User customerUser, double amount) throws PaymentFailed{
        if(customerUser.getPaymentMethod() == null) {
            throw new PaymentFailed("Invalid payment method");
        }
        isSucceeded = true;
        return new PaymentReceipt(LocalDateTime.now(), amount, customerUser);
    }

    public void undoPayment(User customerUser, PaymentReceipt paymentReceipt) throws RefundFailed {
        if(customerUser.getPaymentMethod() == null) {
            throw new RefundFailed("Invalid payment method");
        }
        // refunding ...
        System.out.println("Refunding an amount of " + paymentReceipt.getAmount());
    }
}
