package it.polimi.parkingService.webApplication.parking.services;

import it.polimi.parkingService.webApplication.parking.enums.ParkingSpotStatus;
import it.polimi.parkingService.webApplication.parking.exceptions.ParkingNotTerminated;
import it.polimi.parkingService.webApplication.parking.models.Booking;
import it.polimi.parkingService.webApplication.parking.models.Parking;
import it.polimi.parkingService.webApplication.parking.models.ParkingSpot;
import it.polimi.parkingService.webApplication.payment.models.PaymentReceipt;
import it.polimi.parkingService.webApplication.utils.BaseService;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalTime;

public interface IParkingService extends BaseService<Parking> {


    Parking findActualInProgressParkingBySpot(ParkingSpot spot);
    void update(@Param(value = "id") long id, @Param(value = "estimatedTime") LocalTime estimatedTime);

    public Parking findById(Long id);
}
