package it.polimi.parkingService.webApplication.parking.services;

import it.polimi.parkingService.webApplication.parking.dao.ParkingSpotRepository;
import it.polimi.parkingService.webApplication.parking.enums.ParkingSpotStatus;
import it.polimi.parkingService.webApplication.parking.models.Parking;
import it.polimi.parkingService.webApplication.parking.models.ParkingArea;
import it.polimi.parkingService.webApplication.parking.models.ParkingSpot;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ParkingSpotService implements IParkingSpotService{

    private ParkingSpotRepository parkingSpotRepository;

    private IParkingService parkingService;

    @Autowired
    public ParkingSpotService(ParkingSpotRepository parkingSpotRepository, IParkingService parkingService) {
        this.parkingSpotRepository = parkingSpotRepository;
        this.parkingService = parkingService;
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


    @Override
    @Transactional
    public void update(long id, ParkingSpotStatus status) {
        parkingSpotRepository.update(id, status);
    }

    public Map<ParkingSpot, Parking> getSpotsWithParkings() {
        List<ParkingSpot> parkingSpots = findAll();
        Map<ParkingSpot, Parking> pairs = new LinkedHashMap<>();
        for(ParkingSpot spot : parkingSpots) {
            if(spot.getStatus() == ParkingSpotStatus.BUSY) {
                Parking parking = parkingService.findActualInProgressParkingBySpot(spot);
                pairs.put(spot, parking);
            } else {
                pairs.put(spot, null);
            }
        }
        return pairs;
    }
}
