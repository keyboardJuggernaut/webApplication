package it.polimi.parkingService.webApplication.parking.dao;

import it.polimi.parkingService.webApplication.parking.models.ParkingSpot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkinSpotRepository extends JpaRepository<ParkingSpot, Integer> {
}
