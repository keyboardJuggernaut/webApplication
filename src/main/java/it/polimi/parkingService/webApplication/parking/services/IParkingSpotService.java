package it.polimi.parkingService.webApplication.parking.services;

import it.polimi.parkingService.webApplication.parking.enums.ParkingSpotStatus;
import it.polimi.parkingService.webApplication.parking.models.Parking;
import it.polimi.parkingService.webApplication.parking.models.ParkingSpot;
import it.polimi.parkingService.webApplication.utils.BaseService;
import org.springframework.data.repository.query.Param;

public interface IParkingSpotService extends BaseService<ParkingSpot> {
    void update(@Param(value = "id") long id, @Param(value = "status") ParkingSpotStatus status);
}
