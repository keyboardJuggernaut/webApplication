package it.polimi.parkingService.webApplication.parking.services;

import it.polimi.parkingService.webApplication.account.models.User;
import it.polimi.parkingService.webApplication.parking.models.Booking;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface IBookingService {
    List<Booking> findByDate(LocalDate date);
    int countBookingByCustomerUserAndDate(User customerUser, LocalDate date);

    void save(Booking booking);

    Booking findByCustomerUserAndClaimedFalseAndDate(User customerUser, LocalDate date);

    void update(@Param(value = "id") long id, @Param(value = "claimed") boolean claimed);
}
