package it.polimi.parkingService.webApplication.parking.services;

import com.google.zxing.WriterException;
import it.polimi.parkingService.webApplication.parking.exceptions.ParkingAlreadyInProgress;
import it.polimi.parkingService.webApplication.parking.exceptions.ParkingNotTerminated;
import it.polimi.parkingService.webApplication.parking.models.Booking;
import it.polimi.parkingService.webApplication.parking.models.Parking;
import it.polimi.parkingService.webApplication.parking.models.ParkingArea;
import it.polimi.parkingService.webApplication.parking.models.ParkingSpot;
import it.polimi.parkingService.webApplication.payment.models.PaymentReceipt;
import it.polimi.parkingService.webApplication.utils.BaseService;

import java.io.IOException;
import java.util.Map;

public interface IParkingAreaService extends BaseService<ParkingArea> {
    String getCheckInQRCode(String username) throws ParkingAlreadyInProgress, IOException, WriterException;
    String getCheckOutQRCode(String username) throws ParkingAlreadyInProgress, IOException, WriterException;
    ParkingSpot findParkingSpot (String checkinToken);
    Long getTokenPayload(String claimName, String token);
    PaymentReceipt doCheckout(String token) throws ParkingNotTerminated;

    Map<ParkingSpot, Parking> getSpotsWithParkings();

    Booking reserveParkingSpot(String username,  Booking booking);


    void toggleBlock(long id);
}
