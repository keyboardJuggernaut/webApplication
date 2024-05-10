package it.polimi.parkingService.webApplication.parking.dao;

import it.polimi.parkingService.webApplication.account.models.User;
import it.polimi.parkingService.webApplication.parking.models.Parking;
import it.polimi.parkingService.webApplication.parking.models.ParkingSpot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalTime;
import java.util.Optional;

public interface ParkingRepository extends JpaRepository<Parking, Long> {

    @Query("SELECT p FROM Parking p WHERE p.customerUser = :user AND p.leaving IS null")
    Optional<Parking> findInProgressParkingsByUserId(User user);

    @Query("SELECT p FROM Parking  p WHERE p.spot =:spot AND p.leaving IS null AND p.arrival > CURRENT_DATE")
    Parking findActualInProgressParkingBySpot(ParkingSpot spot);

    @Modifying
    @Query("update Parking u set u.estimatedTime = :estimatedTime where u.id = :id")
    void update(@Param(value = "id") long id, @Param(value = "estimatedTime") LocalTime estimatedTime);

}
