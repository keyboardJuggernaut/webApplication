package it.polimi.parkingService.webApplication.parking.dao;

import it.polimi.parkingService.webApplication.account.models.User;
import it.polimi.parkingService.webApplication.parking.enums.ParkingSpotStatus;
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
    Booking findByCustomerUserAndClaimedFalseAndDate(User customerUser, LocalDate date);

    @Modifying
    @Query("update Booking u set u.claimed = :claimed where u.id = :id")
    void update(@Param(value = "id") long id, @Param(value = "claimed") boolean claimed);

//    @Query("SELECT COUNT(*) FROM Booking b WHERE b.date = :actualDate")
    public Integer countBookingByDate(LocalDate actualDate);
}
