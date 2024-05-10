package it.polimi.parkingService.webApplication.parking.services;

import it.polimi.parkingService.webApplication.parking.enums.ParkingSpotStatus;
import it.polimi.parkingService.webApplication.parking.exceptions.ParkingNotTerminated;
import it.polimi.parkingService.webApplication.parking.models.Booking;
import it.polimi.parkingService.webApplication.parking.models.Parking;
import it.polimi.parkingService.webApplication.parking.models.ParkingSpot;
import it.polimi.parkingService.webApplication.payment.models.PaymentReceipt;
import it.polimi.parkingService.webApplication.utils.BaseService;
import org.springframework.data.repository.query.Param;

import java.util.Map;

public interface IParkingSpotService extends BaseService<ParkingSpot> {
    void update(@Param(value = "id") long id, @Param(value = "status") ParkingSpotStatus status);
    Map<ParkingSpot, Parking> getSpotsWithParkings();

    ParkingSpot findById(long id);
    void toggleSpotAvailability(long id);

    ParkingSpot findParkingSpot (String checkinToken);

    PaymentReceipt doCheckout(String token) throws ParkingNotTerminated;

    Booking reserveParkingSpot(String username, Booking booking);


}
