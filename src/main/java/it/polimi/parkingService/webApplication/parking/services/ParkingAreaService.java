package it.polimi.parkingService.webApplication.parking.services;

import com.google.zxing.WriterException;
import it.polimi.parkingService.webApplication.account.dao.UserDao;
import it.polimi.parkingService.webApplication.account.models.User;
import it.polimi.parkingService.webApplication.account.service.IUserService;
import it.polimi.parkingService.webApplication.parking.dao.ParkingAreaRepository;
import it.polimi.parkingService.webApplication.parking.enums.ParkingSpotStatus;
import it.polimi.parkingService.webApplication.parking.enums.StripeColor;
import it.polimi.parkingService.webApplication.parking.exceptions.ParkingAlreadyInProgress;
import it.polimi.parkingService.webApplication.parking.exceptions.ParkingNotTerminated;
import it.polimi.parkingService.webApplication.parking.exceptions.UnavailableParkingSpot;
import it.polimi.parkingService.webApplication.parking.models.Parking;
import it.polimi.parkingService.webApplication.parking.models.ParkingArea;
import it.polimi.parkingService.webApplication.parking.models.ParkingSpot;
import it.polimi.parkingService.webApplication.parking.strategy.LinearSearchParkingSpotResearchStrategy;
import it.polimi.parkingService.webApplication.parking.strategy.ParkingSpotSearchCriteria;
import it.polimi.parkingService.webApplication.parking.strategy.PriorityQueueParkingSpotResearch;
import it.polimi.parkingService.webApplication.parking.strategy.SearchCriteria;
import it.polimi.parkingService.webApplication.payment.models.PaymentReceipt;
import it.polimi.parkingService.webApplication.payment.models.PaymentSystem;
import it.polimi.parkingService.webApplication.utils.QRCodeGenerator;
import it.polimi.parkingService.webApplication.utils.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ParkingAreaService implements IParkingAreaService{
    @Value("${parking.area.name}")
    private String parkingAreaName;

    private ParkingAreaRepository parkingAreaRepository;

    private IUserService userService;

    private IParkingSpotService parkingSpotService;

    private ParkingService parkingService;

    private TokenGenerator tokenGenerator;

    private PaymentSystem paymentSystem;
    private SseService sseService;




    @Autowired
    public ParkingAreaService(ParkingAreaRepository parkingAreaRepository, IUserService userService, ParkingService parkingService, TokenGenerator tokenGenerator, IParkingSpotService parkingSpotService, PaymentSystem paymentSystem, SseService sseService) {
        this.parkingAreaRepository = parkingAreaRepository;
        this.userService = userService;
        this.parkingService = parkingService;
        this.tokenGenerator = tokenGenerator;
        this.parkingSpotService = parkingSpotService;
        this.paymentSystem = paymentSystem;
        this.sseService = sseService;
    }

    public String getCheckInQRCode(String username) throws ParkingAlreadyInProgress, IOException, WriterException {
        User user = userService.findByUserName(username);
        Optional<Parking> parking = parkingService.findInProgressParkingsByUserId(user);
        if(parking.isPresent()) {
            throw new ParkingAlreadyInProgress(username + " has not terminated the parking yet");
        }
        String token = tokenGenerator.getToken("userId", user.getId());
        return QRCodeGenerator.getQRCodeEncodedImage(token, 100, 100);
    }

    @Override
    public String getCheckOutQRCode(String username) throws ParkingAlreadyInProgress, IOException, WriterException {
        User user = userService.findByUserName(username);
        Optional<Parking> result = parkingService.findInProgressParkingsByUserId(user);
        if(result.isEmpty()) {
            throw new ParkingAlreadyInProgress(username + " has not done the check in yet");
        }
        Parking parking = result.get();
        String token = tokenGenerator.getToken("parkingId", parking.getId());
        return QRCodeGenerator.getQRCodeEncodedImage(token, 100, 100);
    }

    public ParkingSpot findParkingSpot (String checkinToken){
        long userId = getTokenPayload("userId", checkinToken);
        User user = userService.findById(userId);


        ParkingSpotSearchCriteria userSearchCriteria = new ParkingSpotSearchCriteria();
        // is user has both disability and pregnancy condition, set disability as priority
        if(user.getIsPregnant()) {
            userSearchCriteria.setStripeColor(StripeColor.PINK);
        }
        if(user.getIsDisabled()) {
            userSearchCriteria.setStripeColor(StripeColor.YELLOW);
        }

        ParkingArea parkingArea = parkingAreaRepository.findByName(parkingAreaName);

        parkingArea.setParkingSpotResearchStrategy(new LinearSearchParkingSpotResearchStrategy());

        ParkingSpot parkingSpot = parkingArea.searchForParkingSpot(userSearchCriteria);

        // if special spot are not available, search in normal spots, in quelli più vicini possibili
        if(parkingSpot == null && (user.getIsPregnant() || user.getIsDisabled())) {
            userSearchCriteria.setStripeColor(StripeColor.WHITE);
            parkingArea.setParkingSpotResearchStrategy(new PriorityQueueParkingSpotResearch());
            parkingSpot = parkingArea.searchForParkingSpot(userSearchCriteria);
        }

        if(parkingSpot == null) {
            throw new UnavailableParkingSpot("All parking spots are temporarily busy");
        }

        // update status and save
        parkingSpot.setStatus(ParkingSpotStatus.BUSY);
        parkingSpotService.update(parkingSpot.getId(), parkingSpot.getStatus());
        sseService.sendEvent(new Event(parkingSpot.getId(), parkingSpot.getStatus()));

        // create parking and save
        Parking parking = new Parking(user);
        parking.setSpot(parkingSpot);
        parkingService.save(parking);

        return parkingSpot;
    }

    @Override
    public List<ParkingArea> findAll() {
        return parkingAreaRepository.findAll();
    }

    @Override
    public ParkingArea findById(int id) {
        Optional<ParkingArea> result = parkingAreaRepository.findById(id);

        ParkingArea parkingArea = null;

        if (result.isPresent()) {
            parkingArea = result.get();
        }

        return parkingArea;
    }

    @Override
    public ParkingArea save(ParkingArea parkingArea) {
        return parkingAreaRepository.save(parkingArea);
    }

    @Override
    public void deleteById(int id) {
        parkingAreaRepository.deleteById(id);
    }

    public Long getTokenPayload(String claimName, String token) {
        return tokenGenerator.decodeToken(claimName, token);
    }

    @Override
    public PaymentReceipt doCheckout(String token) throws ParkingNotTerminated {
        Parking parking = parkingService.findById(getTokenPayload("parkingId", token));
        parking.setLeaving(LocalDateTime.now());
        parking.pay(paymentSystem);
        parkingService.save(parking);
        ParkingSpot parkingSpot = parking.getSpot();
        parkingSpot.setStatus(ParkingSpotStatus.FREE);
        parkingSpotService.save(parkingSpot);
        sseService.sendEvent(new Event(parkingSpot.getId(), parkingSpot.getStatus()));
        return parking.getPaymentReceipt();
    }

    @Override
    public Map<ParkingSpot, Parking> getSpotsWithParkings() {
        return parkingSpotService.getSpotsWithParkings();
    }
}
