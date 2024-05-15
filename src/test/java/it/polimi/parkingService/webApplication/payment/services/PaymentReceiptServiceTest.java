package it.polimi.parkingService.webApplication.payment.services;

import it.polimi.parkingService.webApplication.payment.dao.PaymentReceiptRepository;
import it.polimi.parkingService.webApplication.payment.models.PaymentReceipt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PaymentReceiptServiceTest {
    @Mock
    private PaymentReceiptRepository paymentReceiptRepository;

    @InjectMocks
    private PaymentReceiptService paymentReceiptService;

    @Test
    public void testFindAmountByDate() {
        LocalDate date = LocalDate.of(2023, 5, 14);
        List<Double> amounts = Arrays.asList(10.0, 20.0, 30.0);
        when(paymentReceiptRepository.findAmountByDate(date)).thenReturn(amounts);

        List<Double> result = paymentReceiptService.findAmountByDate(date);
        assertEquals(amounts, result);
    }

    @Test
    public void testFindAll() {
        List<PaymentReceipt> receipts = Arrays.asList(new PaymentReceipt(), new PaymentReceipt());
        when(paymentReceiptRepository.findAll()).thenReturn(receipts);

        List<PaymentReceipt> result = paymentReceiptService.findAll();
        assertEquals(receipts, result);
    }

    @Test
    public void testFindById_Found() {
        PaymentReceipt receipt = new PaymentReceipt();
        when(paymentReceiptRepository.findById(1L)).thenReturn(Optional.of(receipt));

        PaymentReceipt result = paymentReceiptService.findById(1L);
        assertEquals(receipt, result);
    }

    @Test
    public void testFindById_NotFound() {
        when(paymentReceiptRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> paymentReceiptService.findById(1L));
        assertEquals("Did not find payment receipt id - 1", exception.getMessage());
    }

    @Test
    public void testSave() {
        PaymentReceipt receipt = new PaymentReceipt();
        when(paymentReceiptRepository.save(receipt)).thenReturn(receipt);

        PaymentReceipt result = paymentReceiptService.save(receipt);
        assertEquals(receipt, result);
    }

    @Test
    public void testDeleteById() {
        doNothing().when(paymentReceiptRepository).deleteById(1L);

        paymentReceiptService.deleteById(1L);

        verify(paymentReceiptRepository, times(1)).deleteById(1L);
    }
}
