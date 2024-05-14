package it.polimi.parkingService.webApplication.parking.models;

import it.polimi.parkingService.webApplication.account.models.User;
import it.polimi.parkingService.webApplication.parking.exceptions.ParkingNotTerminated;
import it.polimi.parkingService.webApplication.payment.exceptions.PaymentFailed;
import it.polimi.parkingService.webApplication.payment.models.PaymentReceipt;
import it.polimi.parkingService.webApplication.payment.models.PaymentSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ParkingTest {
    private Parking parking;
    private PaymentSystem paymentSystem;
    private User customerUser;
    private PaymentReceipt paymentReceipt;

    @BeforeEach
    public void setUp() {
        customerUser = new User();
        parking = new Parking(customerUser);
        paymentSystem = mock(PaymentSystem.class);
        paymentReceipt = new PaymentReceipt();
    }

    @Test
    public void testGetParkingCharge_NoLeavingTime_ThrowsParkingNotTerminated() {
        assertThrows(ParkingNotTerminated.class, parking::getParkingCharge);
    }

    @Test
    public void testGetParkingCharge_ParkingChargeCalculation() throws ParkingNotTerminated {
        parking.setLeaving(parking.getArrival().plusMinutes(60));
        double expectedCharge = 60 * Parking.FEE_PER_MINUTE;
        assertEquals(expectedCharge, parking.getParkingCharge(), 0.01);
    }

    @Test
    public void testGetParkingCharge_MinimumCharge() throws ParkingNotTerminated {
        parking.setLeaving(parking.getArrival().plusMinutes(1));
        assertEquals(0.025, parking.getParkingCharge(), 0.01);
    }

    @Test
    public void testPay_Success() throws ParkingNotTerminated, PaymentFailed {
        parking.setLeaving(parking.getArrival().plusMinutes(60));
        when(paymentSystem.processPayment(customerUser, 60 * Parking.FEE_PER_MINUTE)).thenReturn(paymentReceipt);

        parking.pay(paymentSystem);

        assertEquals(paymentReceipt, parking.getPaymentReceipt());
    }

    @Test
    public void testPay_ParkingNotTerminated() {
        assertThrows(ParkingNotTerminated.class, () -> parking.pay(paymentSystem));
    }

    @Test
    public void testPay_PaymentFailed() throws ParkingNotTerminated, PaymentFailed {
        parking.setLeaving(parking.getArrival().plusMinutes(60));
        when(paymentSystem.processPayment(customerUser, 60 * Parking.FEE_PER_MINUTE)).thenThrow(new PaymentFailed("Payment failed"));

        assertThrows(PaymentFailed.class, () -> parking.pay(paymentSystem));
    }
}
