package it.polimi.parkingService.webApplication.payment.dao;

import it.polimi.parkingService.webApplication.payment.models.PaymentReceipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface PaymentReceiptRepository extends JpaRepository<PaymentReceipt, Long> {

    @Query("SELECT p.amount FROM PaymentReceipt p WHERE p.timestamp = :actualDate")
    public List<Double> findAmountByDate(LocalDate actualDate);

    // se non torna amount, valuta se tornare lista receipt di quella giornata e poi creare lista degli amounts
}
