package it.polimi.parkingService.webApplication.parking.services;

import com.google.zxing.WriterException;
import it.polimi.parkingService.webApplication.parking.exceptions.ParkingAlreadyInProgress;
import it.polimi.parkingService.webApplication.parking.models.ParkingArea;
import it.polimi.parkingService.webApplication.parking.models.ParkingSpot;
import it.polimi.parkingService.webApplication.utils.BaseService;

import java.io.IOException;

public interface IParkingAreaService extends BaseService<ParkingArea> {
    public String getCheckInQRCode(String username) throws ParkingAlreadyInProgress, IOException, WriterException;
    public String getCheckOutQRCode(String username) throws ParkingAlreadyInProgress, IOException, WriterException;
    public ParkingSpot findParkingSpot (String checkinToken);
}
