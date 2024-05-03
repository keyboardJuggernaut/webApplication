package it.polimi.parkingService.webApplication.parking.dao;

import it.polimi.parkingService.webApplication.account.models.User;
import it.polimi.parkingService.webApplication.parking.enums.ParkingSpotStatus;
import it.polimi.parkingService.webApplication.parking.models.Parking;
import it.polimi.parkingService.webApplication.parking.models.ParkingSpot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public interface ParkingRepository extends JpaRepository<Parking, Integer> {

    @Query("SELECT p FROM Parking p WHERE p.customerUser = :user AND p.leaving IS null")
    List<Parking> findInProgressParkingsByUserId(User user);

    Parking findBySpotEquals(ParkingSpot parkingSpot);

    @Modifying
    @Query("update Parking u set u.estimatedTime = :estimatedTime where u.id = :id")
    void update(@Param(value = "id") long id, @Param(value = "estimatedTime") LocalTime estimatedTime);
}
