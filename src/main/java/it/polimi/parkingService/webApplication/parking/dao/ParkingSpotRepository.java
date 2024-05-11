package it.polimi.parkingService.webApplication.parking.dao;

import it.polimi.parkingService.webApplication.parking.enums.ParkingSpotStatus;
import it.polimi.parkingService.webApplication.parking.models.Booking;
import it.polimi.parkingService.webApplication.parking.models.Parking;
import it.polimi.parkingService.webApplication.parking.models.ParkingSpot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, Long> {

    @Query("SELECT p FROM Parking p WHERE p.spot = :parkingSpotId")
    List<Parking> findParkingsByParkingSpotId(Long parkingSpotId);

    @Query("SELECT p FROM Parking p WHERE p.spot = :parkingSpotId")
    List<Booking> findBookingsByParkingSpotId(Long parkingSpotId);

    @Modifying
    @Query("update ParkingSpot u set u.status = :status where u.id = :id")
    void update(@Param(value = "id") long id, @Param(value = "status") ParkingSpotStatus status);

    ParkingSpot findById(long id);

    void deleteById(long id);
}
