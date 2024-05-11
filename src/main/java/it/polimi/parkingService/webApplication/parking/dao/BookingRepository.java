package it.polimi.parkingService.webApplication.parking.dao;

import it.polimi.parkingService.webApplication.account.models.User;
import it.polimi.parkingService.webApplication.parking.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByDate(LocalDate date);

    int countBookingByCustomerUserAndDate(User customerUser, LocalDate date);
    Booking findByCustomerUserAndRedeemedFalseAndDate(User customerUser, LocalDate date);

    @Modifying
    @Query("update Booking u set u.redeemed = :redeemed where u.id = :id")
    void update(@Param(value = "id") long id, @Param(value = "redeemed") boolean redeemed);

    Integer countBookingByDate(LocalDate actualDate);

}
