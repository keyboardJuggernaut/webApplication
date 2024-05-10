package it.polimi.parkingService.webApplication.parking.services;

import com.google.zxing.WriterException;
import it.polimi.parkingService.webApplication.account.models.User;
import it.polimi.parkingService.webApplication.account.service.IUserService;
import it.polimi.parkingService.webApplication.parking.dao.ParkingAreaRepository;
import it.polimi.parkingService.webApplication.parking.enums.ParkingSpotStatus;
import it.polimi.parkingService.webApplication.parking.enums.ParkingStripes;
import it.polimi.parkingService.webApplication.parking.exceptions.ParkingAlreadyInProgress;
import it.polimi.parkingService.webApplication.parking.exceptions.ParkingNotTerminated;
import it.polimi.parkingService.webApplication.parking.exceptions.UnavailableParkingSpot;
import it.polimi.parkingService.webApplication.parking.models.Booking;
import it.polimi.parkingService.webApplication.parking.models.Parking;
import it.polimi.parkingService.webApplication.parking.models.ParkingArea;
import it.polimi.parkingService.webApplication.parking.models.ParkingSpot;
import it.polimi.parkingService.webApplication.parking.strategy.LinearSearchParkingSpotResearchStrategy;
import it.polimi.parkingService.webApplication.parking.strategy.ParkingSpotSearchCriteria;
import it.polimi.parkingService.webApplication.parking.strategy.PriorityQueueParkingSpotResearch;
import it.polimi.parkingService.webApplication.payment.models.PaymentReceipt;
import it.polimi.parkingService.webApplication.payment.models.PaymentSystem;
import it.polimi.parkingService.webApplication.utils.QRCodeGenerator;
import it.polimi.parkingService.webApplication.utils.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ParkingAreaService implements IParkingAreaService{


    private ParkingAreaRepository parkingAreaRepository;



    @Autowired
    public ParkingAreaService(ParkingAreaRepository parkingAreaRepository) {
        this.parkingAreaRepository = parkingAreaRepository;
    }

    @Override
    public ParkingArea findByName(String name) {
        return parkingAreaRepository.findByName(name);
    }

    public void save(ParkingArea parkingArea) {
        parkingAreaRepository.save(parkingArea);
    }

}
