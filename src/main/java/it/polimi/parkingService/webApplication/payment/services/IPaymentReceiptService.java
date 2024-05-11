package it.polimi.parkingService.webApplication.payment.services;

import it.polimi.parkingService.webApplication.payment.models.PaymentReceipt;
import it.polimi.parkingService.webApplication.common.BaseService;

import java.time.LocalDate;
import java.util.List;

public interface IPaymentReceiptService extends BaseService<PaymentReceipt> {
    public List<Double> findAmountByDate(LocalDate actualDate);
}
