package it.polimi.parkingService.webApplication.parking.services;


import it.polimi.parkingService.webApplication.parking.dao.ParkingAreaRepository;
import it.polimi.parkingService.webApplication.parking.dao.ParkingRepository;
import it.polimi.parkingService.webApplication.parking.models.ParkingArea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * The {@code ParkingAreaService} handles any parking area related business logic
 */
@Service
public class ParkingAreaService implements IParkingAreaService {


    private final ParkingAreaRepository parkingAreaRepository;

    /**
     * Constructs the services
     * @param parkingAreaRepository the repository handling parking area persistence logic
     */
    @Autowired
    public ParkingAreaService(ParkingAreaRepository parkingAreaRepository) {
        this.parkingAreaRepository = parkingAreaRepository;
    }

    @Override
    public ParkingArea findByName(String name) {
        return parkingAreaRepository.findByName(name);
    }

    @Override
    public List<ParkingArea> findAll() {
        return parkingAreaRepository.findAll();
    }

    @Override
    public ParkingArea findById(long id) {
        Optional<ParkingArea> parkingArea = parkingAreaRepository.findById(id);
        if (parkingArea.isEmpty()) {
            throw new RuntimeException("Did not find parking area - " + id);
        }
        return parkingArea.get();
    }

    public ParkingArea save(ParkingArea parkingArea) {
        return parkingAreaRepository.save(parkingArea);
    }

    @Override
    public void deleteById(long id) {
        parkingAreaRepository.deleteById(id);
    }

}
