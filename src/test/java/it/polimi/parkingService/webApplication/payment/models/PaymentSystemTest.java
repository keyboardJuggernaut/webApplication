package it.polimi.parkingService.webApplication.payment.models;

import it.polimi.parkingService.webApplication.account.models.User;
import it.polimi.parkingService.webApplication.payment.exceptions.PaymentFailed;
import it.polimi.parkingService.webApplication.payment.exceptions.RefundFailed;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PaymentSystemTest {
    private PaymentSystem paymentSystem;
    private User customerUser;
    private PaymentMethod paymentMethod;

    @BeforeEach
    public void setUp() {
        paymentSystem = new PaymentSystem();
        paymentMethod = new PaymentMethod("1234567890123456", "123", LocalDate.of(2025, 12, 31));
        customerUser = new User();
        customerUser.setPaymentMethod(paymentMethod);
        customerUser.setUserName("testUsername");
    }

    @Test
    public void testProcessPaymentSuccess() throws PaymentFailed {
        double amount = 100.0;
        PaymentReceipt receipt = paymentSystem.processPayment(customerUser, amount);

        assertEquals(LocalDate.now(), receipt.getTimestamp());
        assertEquals(amount, receipt.getAmount());
        assertEquals(customerUser, receipt.getCustomerUser());
    }

    @Test
    public void testProcessPaymentFails() {
        customerUser.setPaymentMethod(null);

        assertThrows(PaymentFailed.class, () -> paymentSystem.processPayment(customerUser, 100.0), "Invalid payment method");
    }

    @Test
    public void testUndoPaymentSuccess() throws RefundFailed {
        PaymentReceipt receipt = new PaymentReceipt(LocalDate.now(), 100.0, customerUser);
        paymentSystem.undoPayment(customerUser, receipt);
    }

    @Test
    public void testUndoPaymentFails() {
        customerUser.setPaymentMethod(null);
        PaymentReceipt receipt = new PaymentReceipt(LocalDate.now(), 100.0, customerUser);

        assertThrows(RefundFailed.class, () -> paymentSystem.undoPayment(customerUser, receipt), "Payment method not registered");
    }
}
