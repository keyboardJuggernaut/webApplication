package it.polimi.parkingService.webApplication.payment.models;

import it.polimi.parkingService.webApplication.account.models.Account;
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

    public PaymentReceipt processPayment(Account customerAccount, double amount) throws PaymentFailed{
        if(customerAccount.getPaymentMethod() == null) {
            throw new PaymentFailed("Invalid payment method");
        }
        isSucceeded = true;
        return new PaymentReceipt(LocalDateTime.now(), amount, customerAccount);
    }

    public void undoPayment(Account customerAccount, PaymentReceipt paymentReceipt) throws RefundFailed {
        if(customerAccount.getPaymentMethod() == null) {
            throw new RefundFailed("Invalid payment method");
        }
        // refunding ...
        System.out.println("Refunding an amount of " + paymentReceipt.getAmount());
    }
}
