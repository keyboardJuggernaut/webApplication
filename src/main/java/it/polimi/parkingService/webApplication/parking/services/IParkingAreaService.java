package it.polimi.parkingService.webApplication.parking.services;


import it.polimi.parkingService.webApplication.parking.models.ParkingArea;
import it.polimi.parkingService.webApplication.common.BaseService;

public interface IParkingAreaService extends BaseService<ParkingArea> {

    ParkingArea findByName(String name);

}
