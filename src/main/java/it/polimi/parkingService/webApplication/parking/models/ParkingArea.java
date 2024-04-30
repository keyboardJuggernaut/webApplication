package it.polimi.parkingService.webApplication.parking.models;

import it.polimi.parkingService.webApplication.parking.strategy.LinearSearchParkingSpotResearchStrategy;
import it.polimi.parkingService.webApplication.parking.strategy.ParkingSpotResearchStrategy;
import it.polimi.parkingService.webApplication.parking.strategy.SearchCriteria;
import it.polimi.parkingService.webApplication.utils.BaseEntity;
import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Class model for parking area
 * Note: the set of parking spots is represented as a quadratic matrix
 */
@Entity
//@Table(name="parking_area")
public class ParkingArea extends BaseEntity {

    @Column(name="name")
    private String name;

    @Column(name="order")
    private int order;

    @OneToMany(
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    private List<ParkingSpot> parkingSpots;

    @Transient
    private ParkingSpotResearchStrategy parkingSpotResearchStrategy;

    public ParkingArea(String name, int order) {
        this.name = name;
        this.order = order;
    }

    public ParkingArea(){}

    public void setParkingSpot(ParkingSpot parkingSpot) throws IndexOutOfBoundsException {
        if(parkingSpots == null){
            parkingSpots = new ArrayList<>(order*order);
        }

        parkingSpots.add(getFlattenedArrayIndex(parkingSpot.getRowNumber(), parkingSpot.getColumnNumber()), parkingSpot);
        parkingSpot.setParkingArea(this);
    }

    public ParkingSpot getParkingSpot(int row, int column) throws IndexOutOfBoundsException {
        if(parkingSpots != null) {
            return parkingSpots.get(getFlattenedArrayIndex(row, column));
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
        return parkingSpotResearchStrategy.findSpot(convertToMatrix(), searchCriteria);
    }

    private int getFlattenedArrayIndex(int row, int column) throws IndexOutOfBoundsException {
        if (row < 0 || row >= order || column < 0 || column >= order) {
            throw new IndexOutOfBoundsException("Invalid row or column index");
        }
        return row * order + column;
    }

    private ParkingSpot[][] convertToMatrix() {
        if (parkingSpots.size() != order * order) {
            throw new IllegalArgumentException("Array size does not match matrix dimensions");
        }

        // Initialize matrix with the given dimensions
        ParkingSpot[][] parkingSpotsMatrix = new ParkingSpot[order][order];

        // Populate matrix from 1D array
        for (int i = 0; i < order; i++) {
            for (int j = 0; j < order; j++) {
                // Calculate index in the 1D array
                int index = i * order + j;
                // Set the corresponding element in the matrix
                parkingSpotsMatrix[i][j] = parkingSpots.get(index);
            }
        }
        return parkingSpotsMatrix;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ParkingSpot> getParkingSpots() {
        return parkingSpots;
    }

    public void setParkingSpots(List<ParkingSpot> parkingSpots) {
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