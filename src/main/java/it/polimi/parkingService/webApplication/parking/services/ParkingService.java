package it.polimi.parkingService.webApplication.parking.services;

import it.polimi.parkingService.webApplication.account.models.User;
import it.polimi.parkingService.webApplication.parking.dao.ParkingRepository;
import it.polimi.parkingService.webApplication.parking.models.Parking;
import it.polimi.parkingService.webApplication.parking.models.ParkingSpot;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class ParkingService implements IParkingService{

    private ParkingRepository parkingRepository;

    @Autowired
    public ParkingService(ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }

    @Override
    public List<Parking> findAll() {
        return parkingRepository.findAll();
    }

    @Override
    public Parking findById(int id) {
        Optional<Parking> result = parkingRepository.findById(id);

        Parking parking = null;

        if (result.isPresent()) {
            parking = result.get();
        }
        else {
            // we didn't find the employee
            throw new RuntimeException("Did not find parking - " + id);
        }

        return parking;
    }

    @Override
    public Parking save(Parking entity) {
        return parkingRepository.save(entity);
    }

    @Override
    public void deleteById(int id) {
        parkingRepository.deleteById(id);
    }

    public List<Parking> findInProgressParkingsByUserId(User user) {
        return parkingRepository.findInProgressParkingsByUserId(user);
    }

    @Override
    public Parking findBySpotEquals(ParkingSpot parkingSpot) {
        return parkingRepository.findBySpotEquals(parkingSpot);
    }

    @Override
    @Transactional
    public void update(long id, LocalTime estimatedTime) {
        parkingRepository.update(id, estimatedTime);
    }
}
