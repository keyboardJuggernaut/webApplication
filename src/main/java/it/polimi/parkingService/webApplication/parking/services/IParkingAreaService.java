package it.polimi.parkingService.webApplication.parking.services;


import it.polimi.parkingService.webApplication.parking.models.ParkingArea;

public interface IParkingAreaService  {

    ParkingArea findByName(String name);

    void save(ParkingArea parkingArea);
}
