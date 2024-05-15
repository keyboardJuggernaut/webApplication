package it.polimi.parkingService.webApplication.payment.models;

import it.polimi.parkingService.webApplication.account.models.User;
import it.polimi.parkingService.webApplication.parking.models.Booking;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaymentReceiptTest {
    @Test
    public void testPaymentReceiptConstructorAndGetters() {
        LocalDate timestamp = LocalDate.of(2023, 5, 14);
        User customerUser = new User();
        PaymentReceipt paymentReceipt = new PaymentReceipt(timestamp, 100.0, customerUser);

        assertEquals(timestamp, paymentReceipt.getTimestamp());
        assertEquals(100.0, paymentReceipt.getAmount());
        assertEquals(customerUser, paymentReceipt.getCustomerUser());
    }

    @Test
    public void testPaymentReceiptSetters() {
        LocalDate timestamp = LocalDate.of(2023, 5, 14);
        User customerUser = new User();
        Booking booking = new Booking();
        PaymentReceipt paymentReceipt = new PaymentReceipt();

        paymentReceipt.setTimestamp(timestamp);
        paymentReceipt.setAmount(100.0);
        paymentReceipt.setCustomerUser(customerUser);
        paymentReceipt.setBooking(booking);

        assertEquals(timestamp, paymentReceipt.getTimestamp());
        assertEquals(100.0, paymentReceipt.getAmount());
        assertEquals(customerUser, paymentReceipt.getCustomerUser());
        assertEquals(booking, paymentReceipt.getBooking());
    }
}
