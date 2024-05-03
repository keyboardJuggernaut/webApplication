package it.polimi.parkingService.webApplication.parking.dao;

import it.polimi.parkingService.webApplication.parking.models.Booking;
import it.polimi.parkingService.webApplication.parking.models.Parking;
import it.polimi.parkingService.webApplication.parking.models.ParkingSpot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, Integer> {

    @Query("SELECT p FROM Parking p WHERE p.spot = :parkingSpotId")
    List<Parking> findParkingsByParkingSpotId(Long parkingSpotId);

    @Query("SELECT p FROM Parking p WHERE p.spot = :parkingSpotId")
    List<Booking> findBookingsByParkingSpotId(Long parkingSpotId);
}
