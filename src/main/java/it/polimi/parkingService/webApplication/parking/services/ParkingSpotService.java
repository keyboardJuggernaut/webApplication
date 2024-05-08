package it.polimi.parkingService.webApplication.parking.services;

import it.polimi.parkingService.webApplication.parking.dao.ParkingSpotRepository;
import it.polimi.parkingService.webApplication.parking.enums.ParkingSpotStatus;
import it.polimi.parkingService.webApplication.parking.models.Booking;
import it.polimi.parkingService.webApplication.parking.models.Parking;
import it.polimi.parkingService.webApplication.parking.models.ParkingArea;
import it.polimi.parkingService.webApplication.parking.models.ParkingSpot;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class ParkingSpotService implements IParkingSpotService{

    private ParkingSpotRepository parkingSpotRepository;

    private IParkingService parkingService;

    private IBookingService bookingService;

    @Autowired
    public ParkingSpotService(ParkingSpotRepository parkingSpotRepository, IParkingService parkingService, IBookingService bookingService) {
        this.parkingSpotRepository = parkingSpotRepository;
        this.parkingService = parkingService;
        this.bookingService = bookingService;
    }

    @Override
    public List<ParkingSpot> findAll() {
        return parkingSpotRepository.findAll();
    }

    @Override
     @Transactional
    public ParkingSpot findById(int id) {
        ParkingSpot spot = parkingSpotRepository.findById(id);

        if (spot == null) {
            throw new RuntimeException("Did not find parking spot - " + id);
        }

        return spot;
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

        List<Booking> bookings = bookingService.findByDate(LocalDate.now());
        List<Long> reservedSpots = new ArrayList<>();
        if(!bookings.isEmpty()) {
            for (Booking reservation : bookings) {
                if(!reservation.getClaimed()) {
                    reservedSpots.add(reservation.getParkingSpot().getId());
                }
            }
        }

        for(ParkingSpot spot : parkingSpots) {

            if(!bookings.isEmpty() && reservedSpots.contains(spot.getId())) {
                spot.setStatus(ParkingSpotStatus.RESERVED);
            }

            if(spot.getStatus() == ParkingSpotStatus.BUSY) {
                Parking parking = parkingService.findActualInProgressParkingBySpot(spot);
                pairs.put(spot, parking);
            } else {
                pairs.put(spot, null);
            }
        }
        return pairs;
    }

    @Override
    public ParkingSpot findById(long id) {
        return parkingSpotRepository.findById(id);
    }
}
