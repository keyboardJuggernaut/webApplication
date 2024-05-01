package it.polimi.parkingService.webApplication.parking.dao;

import it.polimi.parkingService.webApplication.parking.models.ParkingSpot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, Integer> {
}
