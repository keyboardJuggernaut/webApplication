package it.polimi.parkingService.webApplication.parking.services;

import com.google.zxing.WriterException;
import it.polimi.parkingService.webApplication.account.models.User;
import it.polimi.parkingService.webApplication.parking.exceptions.ParkingAlreadyInProgress;
import it.polimi.parkingService.webApplication.parking.exceptions.ParkingNotInProgress;
import it.polimi.parkingService.webApplication.parking.models.Parking;
import it.polimi.parkingService.webApplication.parking.models.ParkingSpot;
import it.polimi.parkingService.webApplication.common.BaseService;
import org.springframework.data.repository.query.Param;

import java.io.IOException;
import java.time.LocalTime;
import java.util.Optional;

public interface IParkingService extends BaseService<Parking> {
    String getCheckInQRCode(String username) throws ParkingAlreadyInProgress, IOException, WriterException, ParkingNotInProgress;
    Parking findActualInProgressParkingBySpot(ParkingSpot spot);
    void update(@Param(value = "id") long id, @Param(value = "estimatedTime") LocalTime estimatedTime);
    String getCheckOutQRCode(String username) throws ParkingAlreadyInProgress, IOException, WriterException, ParkingNotInProgress;
    Optional<Parking> findInProgressParkingsByUserId(User user);

}
