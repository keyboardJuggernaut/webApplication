package it.polimi.parkingService.webApplication.parking.models;

import it.polimi.parkingService.webApplication.parking.strategy.LinearSearchParkingSpotResearchStrategy;
import it.polimi.parkingService.webApplication.parking.strategy.ParkingSpotResearchStrategy;
import it.polimi.parkingService.webApplication.parking.utils.SearchCriteria;
import org.springframework.stereotype.Component;

/**
 * Class model for parking area
 * Note: the set of parking spots is represented as a quadratic matrix
 */
@Component
public class ParkingArea {
    private String name;
    private int order;

    private ParkingSpot[][] parkingSpots;
    private ParkingSpotResearchStrategy parkingSpotResearchStrategy;

    public ParkingArea(String name, int order) {
        this.name = name;
        this.order = order;
    }

    public ParkingArea(){}

    public void setParkingSpot(ParkingSpot parkingSpot) {
        if(parkingSpots == null){
            parkingSpots = new ParkingSpot[order][order];
        }
        parkingSpots[parkingSpot.getRowNumber()][parkingSpot.getColumnNumber()] = parkingSpot;
    }

    public ParkingSpot getParkingSpot(int row, int column) {
        if(parkingSpots != null) {
            return parkingSpots[row][column];
        }
        return null;
    }

    public void setParkingSpotResearchStrategy(ParkingSpotResearchStrategy parkingSpotResearchStrategy) {
        this.parkingSpotResearchStrategy = parkingSpotResearchStrategy;
    }

    public ParkingSpot searchForParkingSpot(SearchCriteria searchCriteria) {
        if(parkingSpotResearchStrategy == null) {
            parkingSpotResearchStrategy = new LinearSearchParkingSpotResearchStrategy();
        }
        return parkingSpotResearchStrategy.findSpot(parkingSpots, searchCriteria);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ParkingSpot[][] getParkingSpots() {
        return parkingSpots;
    }

    public void setParkingSpots(ParkingSpot[][] parkingSpots) {
        this.parkingSpots = parkingSpots;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "ParkingArea{" +
                "name='" + name + '\'' +
                '}';
    }
}
