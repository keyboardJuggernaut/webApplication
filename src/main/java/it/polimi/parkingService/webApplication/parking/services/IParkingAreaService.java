package it.polimi.parkingService.webApplication.parking.services;

import com.google.zxing.WriterException;
import it.polimi.parkingService.webApplication.parking.exceptions.ParkingAlreadyInProgress;
import it.polimi.parkingService.webApplication.parking.exceptions.ParkingNotTerminated;
import it.polimi.parkingService.webApplication.parking.models.Parking;
import it.polimi.parkingService.webApplication.parking.models.ParkingArea;
import it.polimi.parkingService.webApplication.parking.models.ParkingSpot;
import it.polimi.parkingService.webApplication.payment.models.PaymentReceipt;
import it.polimi.parkingService.webApplication.utils.BaseService;

import java.io.IOException;
import java.util.Map;

public interface IParkingAreaService extends BaseService<ParkingArea> {
    public String getCheckInQRCode(String username) throws ParkingAlreadyInProgress, IOException, WriterException;
    public String getCheckOutQRCode(String username) throws ParkingAlreadyInProgress, IOException, WriterException;
    public ParkingSpot findParkingSpot (String checkinToken);
    public Long getTokenPayload(String claimName, String token);
    public PaymentReceipt doCheckout(String token) throws ParkingNotTerminated;

    public Map<ParkingSpot, Parking> getSpotsWithParkings();


}
