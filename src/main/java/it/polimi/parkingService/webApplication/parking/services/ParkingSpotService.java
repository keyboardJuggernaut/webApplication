package it.polimi.parkingService.webApplication.parking.services;

import it.polimi.parkingService.webApplication.parking.dao.ParkingSpotRepository;
import it.polimi.parkingService.webApplication.parking.models.ParkingArea;
import it.polimi.parkingService.webApplication.parking.models.ParkingSpot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParkingSpotService implements IParkingSpotService{

    private ParkingSpotRepository parkingSpotRepository;

    @Autowired
    public ParkingSpotService(ParkingSpotRepository parkingSpotRepository) {
        this.parkingSpotRepository = parkingSpotRepository;
    }

    @Override
    public List<ParkingSpot> findAll() {
        return parkingSpotRepository.findAll();
    }

    @Override
    public ParkingSpot findById(int id) {
        Optional<ParkingSpot> result = parkingSpotRepository.findById(id);

        ParkingSpot parkingSpot = null;

        if (result.isPresent()) {
            parkingSpot = result.get();
        }
        else {
            // we didn't find the employee
            throw new RuntimeException("Did not find parking spot - " + id);
        }

        return parkingSpot;
    }

    @Override
    public ParkingSpot save(ParkingSpot entity) {
        return parkingSpotRepository.save(entity);
    }

    @Override
    public void deleteById(int id) {
        parkingSpotRepository.deleteById(id);
    }
}
