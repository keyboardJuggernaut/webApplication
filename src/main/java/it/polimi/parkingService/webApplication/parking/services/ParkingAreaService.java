package it.polimi.parkingService.webApplication.parking.services;

import com.google.zxing.WriterException;
import it.polimi.parkingService.webApplication.account.dao.UserDao;
import it.polimi.parkingService.webApplication.account.models.User;
import it.polimi.parkingService.webApplication.account.service.IUserService;
import it.polimi.parkingService.webApplication.parking.dao.ParkingAreaRepository;
import it.polimi.parkingService.webApplication.parking.exceptions.ParkingAlreadyInProgress;
import it.polimi.parkingService.webApplication.parking.models.Parking;
import it.polimi.parkingService.webApplication.parking.models.ParkingArea;
import it.polimi.parkingService.webApplication.parking.models.ParkingSpot;
import it.polimi.parkingService.webApplication.utils.QRCodeGenerator;
import it.polimi.parkingService.webApplication.utils.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ParkingAreaService implements IParkingAreaService{

    private ParkingAreaRepository parkingAreaRepository;

    private IUserService userService;

    private ParkingService parkingService;

    private TokenGenerator tokenGenerator;


    @Autowired
    public ParkingAreaService(ParkingAreaRepository parkingAreaRepository, IUserService userService, ParkingService parkingService, TokenGenerator tokenGenerator, QRCodeGenerator qrCodeGenerator) {
        this.parkingAreaRepository = parkingAreaRepository;
        this.userService = userService;
        this.parkingService = parkingService;
        this.tokenGenerator = tokenGenerator;
    }

    public String getCheckInQRCode(String username) throws ParkingAlreadyInProgress, IOException, WriterException {
        User user = userService.findByUserName(username);
        List<Parking> parkings = parkingService.findInProgressParkingsByUserId(user);
        if(!parkings.isEmpty()) {
            throw new ParkingAlreadyInProgress(username + " has not terminated the parking yet");
        }
        String token = tokenGenerator.getToken(user.getId());
        return QRCodeGenerator.getQRCodeEncodedImage(token, 100, 100);
    }

    public ParkingSpot findParkingSpot (String checkinToken){
        long userId = tokenGenerator.decodeToken(checkinToken);
        // trova utente
        // crea criteri in base all'utente
        // crea e setta strategia
        // cerca parcheggio con strategia
        // se c'è crea parking
        // ritorn parking spot e mostra l'identifier
        // aggiungi form per aggiungere tempo di permanenza previsto
        return new ParkingSpot();
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
}
