package it.polimi.parkingService.webApplication.payment.services;

import it.polimi.parkingService.webApplication.payment.dao.PaymentReceiptRepository;
import it.polimi.parkingService.webApplication.payment.models.PaymentReceipt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * The {@code PaymentReceiptService} handles payment receipt related business logic
 */
@Service
public class PaymentReceiptService implements IPaymentReceiptService {
    private final PaymentReceiptRepository paymentReceiptRepository;

    @Autowired
    public PaymentReceiptService(PaymentReceiptRepository paymentReceiptRepository) {
        this.paymentReceiptRepository = paymentReceiptRepository;
    }

    @Override
    public List<Double> findAmountByDate(LocalDate actualDate) {
        return paymentReceiptRepository.findAmountByDate(actualDate);
    }

    @Override
    public List<PaymentReceipt> findAll() {
        return paymentReceiptRepository.findAll();
    }

    @Override
    public PaymentReceipt findById(long id) {
        Optional<PaymentReceipt> result = paymentReceiptRepository.findById(id);

        if(result.isEmpty()) {
            throw new RuntimeException("Did not find payment receipt id - " + id);
        }

        return result.get();
    }

    @Override
    public PaymentReceipt save(PaymentReceipt entity) {
        return paymentReceiptRepository.save(entity);
    }

    @Override
    public void deleteById(long id) {
        paymentReceiptRepository.deleteById(id);
    }
}
