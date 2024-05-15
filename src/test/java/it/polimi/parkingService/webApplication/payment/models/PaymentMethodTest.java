package it.polimi.parkingService.webApplication.payment.models;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaymentMethodTest {
    @Test
    public void testPaymentMethodConstructorAndGetters() {
        LocalDate expirationDate = LocalDate.of(2025, 12, 31);
        PaymentMethod paymentMethod = new PaymentMethod("1234567890123456", "123", expirationDate);

        assertEquals("1234567890123456", paymentMethod.getPan());
        assertEquals("123", paymentMethod.getCvv());
        assertEquals(expirationDate, paymentMethod.getExpirationDate());
    }

    @Test
    public void testPaymentMethodSetters() {
        LocalDate expirationDate = LocalDate.of(2025, 12, 31);
        PaymentMethod paymentMethod = new PaymentMethod();

        paymentMethod.setPan("1234567890123456");
        paymentMethod.setCvv("123");
        paymentMethod.setExpirationDate(expirationDate);

        assertEquals("1234567890123456", paymentMethod.getPan());
        assertEquals("123", paymentMethod.getCvv());
        assertEquals(expirationDate, paymentMethod.getExpirationDate());
    }
}
