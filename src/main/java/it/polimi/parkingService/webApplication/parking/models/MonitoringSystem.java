package it.polimi.parkingService.webApplication.parking.models;

import it.polimi.parkingService.webApplication.parking.enums.ParkingSpotStatus;

import java.util.List;

public interface MonitoringSystem {

    // get the total number of parking spots
    int getTotalSpots();

    // get the number of available parking spots
    int getAvailableSpots();

    // get the number of busy parking spots
    int getBusySpots();

    // get the list of vehicles currently parked
    List<Vehicle> getParkedVehicles();

    // add a vehicle to the parked vehicles list
    void addParkedVehicle(Vehicle vehicle);

    // remove a vehicle from the parked vehicles list
    void removeParkedVehicle(Vehicle vehicle);

    // check if a specific parking space is available
    boolean isParkingSpotAvailable(int spotIdentifier);

    // get the status of a specific parking space
    ParkingSpotStatus getParkingSpotStatus(int spotIdentifier);

    // update the status of a specific parking space
    void updateParkingSpotStatus(int spotIdentifier, ParkingSpotStatus status);

    // turn off the monitoring system
    void turnOff();
}
