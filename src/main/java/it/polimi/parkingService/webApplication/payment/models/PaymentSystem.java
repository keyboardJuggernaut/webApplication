package it.polimi.parkingService.webApplication.payment.models;

import it.polimi.parkingService.webApplication.account.models.User;
import it.polimi.parkingService.webApplication.payment.exceptions.PaymentFailed;
import it.polimi.parkingService.webApplication.payment.exceptions.RefundFailed;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

/**
 * The {@code PaymentSystem} handles any payment related business logic
 */
@Component
public class PaymentSystem {


    /**
     * Does payment transaction
     * @param customerUser user paying
     * @param amount to pay
     * @return payment receipt
     * @throws PaymentFailed if payment fails
     */
    public PaymentReceipt processPayment(User customerUser, double amount) throws PaymentFailed{
        // check if user has payment coordinates
        if(customerUser.getPaymentMethod() == null) {
            throw new PaymentFailed("Invalid payment method");
        }
        // do transaction
        return new PaymentReceipt(LocalDate.now(), amount, customerUser);
    }

    /**
     * Undoes payment transactions
     * @param customerUser the user
     * @param paymentReceipt the receipt with payment details
     * @throws RefundFailed when there are no user payment coordinates
     */
    public void undoPayment(User customerUser, PaymentReceipt paymentReceipt) throws RefundFailed {
        // check if user has payment coordinates
        if(customerUser.getPaymentMethod() == null) {
            throw new RefundFailed("Payment method not registered");
        }

        // undo transaction
        System.out.println("Refunding an amount of " + paymentReceipt.getAmount() + "to " + customerUser.getUserName());
    }

}
