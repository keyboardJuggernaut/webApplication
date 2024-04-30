package it.polimi.parkingService.webApplication.parking.dao;

import it.polimi.parkingService.webApplication.parking.models.Parking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingRepository extends JpaRepository<Parking, Integer> {
}
