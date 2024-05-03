package it.polimi.parkingService.webApplication.parking.dao;

import it.polimi.parkingService.webApplication.account.models.User;
import it.polimi.parkingService.webApplication.parking.models.Parking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ParkingRepository extends JpaRepository<Parking, Integer> {

    @Query("SELECT p FROM Parking p WHERE p.customerUser = :user AND p.arrival = null")
    List<Parking> findInProgressParkingsByUserId(User user);
}
