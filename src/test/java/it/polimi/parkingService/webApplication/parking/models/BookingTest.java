package it.polimi.parkingService.webApplication.parking.models;

import it.polimi.parkingService.webApplication.account.models.User;
import it.polimi.parkingService.webApplication.payment.exceptions.PaymentFailed;
import it.polimi.parkingService.webApplication.payment.exceptions.RefundFailed;
import it.polimi.parkingService.webApplication.payment.models.PaymentReceipt;
import it.polimi.parkingService.webApplication.payment.models.PaymentSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class BookingTest {
    private Booking booking;
    private PaymentSystem paymentSystem;
    private User customerUser;
    private PaymentReceipt paymentReceipt;

    @BeforeEach
    public void setUp() {
        booking = new Booking(LocalDate.now().plusDays(5));
        customerUser = new User();
        paymentSystem = mock(PaymentSystem.class);
        paymentReceipt = new PaymentReceipt();

        booking.setCustomerUser(customerUser);
        booking.setRedeemed(false);
    }

    @Test
    public void testPay_Success() throws PaymentFailed {
        when(paymentSystem.processPayment(customerUser, Booking.DAILY_CHARGE)).thenReturn(paymentReceipt);

        booking.pay(paymentSystem);

        assertEquals(paymentReceipt, booking.getPaymentReceipt());
    }

    @Test
    public void testRefundCheck_EligibleForRefund() throws RefundFailed {
        booking.setPaymentReceipt(paymentReceipt);
        LocalDate bookingDate = LocalDate.now().plusDays(5);
        booking.setDate(bookingDate);

        doNothing().when(paymentSystem).undoPayment(customerUser, paymentReceipt);

        assertDoesNotThrow(() -> booking.refundCheck(paymentSystem, false));
//        verify(paymentSystem, times(1)).undoPayment(customerUser, paymentReceipt);
    }

    @Test
    public void testRefundCheck_NotEligibleForRefund() throws RefundFailed {
        booking.setPaymentReceipt(paymentReceipt);
        LocalDate bookingDate = LocalDate.now().minusDays(1);
        booking.setDate(bookingDate);

        assertDoesNotThrow(() -> booking.refundCheck(paymentSystem, false));
        verify(paymentSystem, times(0)).undoPayment(customerUser, paymentReceipt);
    }

    @Test
    public void testRefundCheck_AdminAction() throws RefundFailed {
        booking.setPaymentReceipt(paymentReceipt);

        doNothing().when(paymentSystem).undoPayment(customerUser, paymentReceipt);

        assertDoesNotThrow(() -> booking.refundCheck(paymentSystem, true));
        verify(paymentSystem, times(1)).undoPayment(customerUser, paymentReceipt);
    }

    @Test
    public void testRefundCheck_AlreadyRedeemed() throws RefundFailed {
        booking.setPaymentReceipt(paymentReceipt);
        booking.setRedeemed(true);

        assertDoesNotThrow(() -> booking.refundCheck(paymentSystem, false));
        verify(paymentSystem, times(0)).undoPayment(customerUser, paymentReceipt);
    }
}
