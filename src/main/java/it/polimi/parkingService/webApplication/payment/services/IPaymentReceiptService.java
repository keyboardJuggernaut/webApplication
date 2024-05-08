package it.polimi.parkingService.webApplication.payment.services;

import java.time.LocalDate;
import java.util.List;

public interface IPaymentReceiptService {
    public List<Double> findAmountByDate(LocalDate actualDate);
}
