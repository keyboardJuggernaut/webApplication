package it.polimi.parkingService.webApplication.parking.services;

import com.google.zxing.WriterException;
import it.polimi.parkingService.webApplication.account.models.User;
import it.polimi.parkingService.webApplication.account.services.IUserService;
import it.polimi.parkingService.webApplication.parking.dao.ParkingRepository;
import it.polimi.parkingService.webApplication.parking.exceptions.ParkingAlreadyInProgress;
import it.polimi.parkingService.webApplication.parking.exceptions.ParkingNotInProgress;
import it.polimi.parkingService.webApplication.parking.models.Parking;
import it.polimi.parkingService.webApplication.parking.models.ParkingSpot;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

/**
 * The {@code ParkingService} handles any parking related business logic
 */
@Service
public class ParkingService implements IParkingService{

    private final ParkingRepository parkingRepository;

    private final IUserService userService;

    private final IQRCodeService qrCodeService;

    /**
     * Constructs the services
     * @param parkingRepository the repository handling parking persistence logic
     * @param userService the services handling user business logic
     * @param qrCodeService the services handling qrcode business logic
     */
    @Autowired
    public ParkingService(ParkingRepository parkingRepository, IUserService userService, IQRCodeService qrCodeService) {
        this.parkingRepository = parkingRepository;
        this.userService = userService;
        this.qrCodeService = qrCodeService;
    }

    /**
     * Generates qrcode to check in, if no parking is already in progress
     * @param username authenticated username
     * @return qrcode representation
     * @throws ParkingAlreadyInProgress if there is already a parking in progress
     * @throws IOException if IO fails
     * @throws WriterException if writing fails
     */
    public String getCheckInQRCode(String username) throws ParkingAlreadyInProgress, IOException, WriterException {
        User user = userService.findByUserName(username);
        Optional<Parking> parking = findInProgressParkingsByUserId(user);
        if(parking.isPresent()) {
            throw new ParkingAlreadyInProgress(username + " has not terminated the parking yet");
        }
        return qrCodeService.getQRCodeWithEmbeddedToken("userId", user.getId());
    }

    /**
     * Generates qrcode to check out, if a parking is in progress
     * @param username authenticated username
     * @return qrcode representation
     * @throws ParkingAlreadyInProgress if there is already a parking in progress
     * @throws IOException if IO fails
     * @throws WriterException if writing fails
     */
    @Override
    public String getCheckOutQRCode(String username) throws ParkingAlreadyInProgress, IOException, WriterException, ParkingNotInProgress {
        User user = userService.findByUserName(username);
        Optional<Parking> result = findInProgressParkingsByUserId(user);
        if(result.isEmpty()) {
            throw new ParkingNotInProgress(username + " has not done the check-in yet");
        }
        Parking parking = result.get();
        return qrCodeService.getQRCodeWithEmbeddedToken("parkingId", parking.getId());
    }


    @Override
    public List<Parking> findAll() {
        return parkingRepository.findAll();
    }

    @Override
    public Parking findById(long id) {
        Optional<Parking> parking = parkingRepository.findById(id);
        if (parking.isEmpty()) {
            throw new RuntimeException("Did not find parking - " + id);
        }
        return parking.get();
    }

    @Override
    public Parking save(Parking entity) {
        return parkingRepository.save(entity);
    }

    @Override
    public void deleteById(long id) {
        parkingRepository.deleteById(id);
    }

    @Override
    public Optional<Parking> findInProgressParkingsByUserId(User user) {
        return parkingRepository.findInProgressParkingsByUserId(user);
    }

    @Override
    public Parking findActualInProgressParkingBySpot(ParkingSpot spot) {
        return parkingRepository.findActualInProgressParkingBySpot(spot);
    }

    @Override
    @Transactional
    public void update(long id, LocalTime estimatedTime) {
        parkingRepository.update(id, estimatedTime);
    }
}
