package it.polimi.parkingService.webApplication.payment.services;

import it.polimi.parkingService.webApplication.payment.dao.PaymentReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PaymentReceiptService implements IPaymentReceiptService{
    private PaymentReceiptRepository paymentReceiptRepository;

    @Autowired
    public PaymentReceiptService(PaymentReceiptRepository paymentReceiptRepository) {
        this.paymentReceiptRepository = paymentReceiptRepository;
    }

    @Override
    public List<Double> findAmountByDate(LocalDate actualDate) {
        return paymentReceiptRepository.findAmountByDate(actualDate);
    }
}
