package it.polimi.parkingService.webApplication.parking.services;

import it.polimi.parkingService.webApplication.parking.dao.ParkingAreaRepository;
import it.polimi.parkingService.webApplication.parking.models.ParkingArea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParkingAreaService implements IParkingAreaService{

    private ParkingAreaRepository parkingAreaRepository;

    @Autowired
    public ParkingAreaService(ParkingAreaRepository parkingAreaRepository) {
        this.parkingAreaRepository = parkingAreaRepository;
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
        else {
            // we didn't find the employee
            throw new RuntimeException("Did not find employee id - " + id);
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
