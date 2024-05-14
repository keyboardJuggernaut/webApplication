package it.polimi.parkingService.webApplication.parking.models;

import it.polimi.parkingService.webApplication.parking.exceptions.SearchStrategyUndefined;
import it.polimi.parkingService.webApplication.parking.strategy.ParkingSpotResearchStrategy;
import it.polimi.parkingService.webApplication.parking.strategy.SearchCriteria;
import it.polimi.parkingService.webApplication.common.BaseEntity;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code ParkingArea} represents parking area model
 * Note: the set of parking spots is represented as a quadratic matrix
 */
@Entity
@Table(name="parking_area")
public class ParkingArea extends BaseEntity {

    @Column(name="name")
    private String name;

    @Column(name="matrix_order")
    private int order;

    @OneToMany(
            mappedBy = "parkingArea",
            fetch = FetchType.EAGER,
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

    /**
     * Chooses a spot based on certain criteria with a certain strategy
     * @param searchCriteria custom criteria
     * @return spot found
     * @throws SearchStrategyUndefined if strategy has not been set
     */
    public ParkingSpot searchForParkingSpot(SearchCriteria searchCriteria) throws SearchStrategyUndefined {
        if(parkingSpotResearchStrategy == null) {
            throw new SearchStrategyUndefined("Search strategy has not been set");
        }
        return parkingSpotResearchStrategy.findSpot(convertToMatrix(), searchCriteria);
    }

    private int getFlattenedArrayIndex(int row, int column) throws IndexOutOfBoundsException {
        if (row < 0 || row >= order || column < 0 || column >= order) {
            throw new IndexOutOfBoundsException("Invalid row or column index");
        }
        return row * order + column;
    }

    ParkingSpot[][] convertToMatrix() {
        if (parkingSpots.size() != order * order) {
            throw new IllegalArgumentException("Array size does not match matrix dimensions");
        }

        ParkingSpot[][] parkingSpotsMatrix = new ParkingSpot[order][order];

        for (int i = 0; i < order; i++) {
            for (int j = 0; j < order; j++) {
                // Calculate index in the 1D array
                int index = i * order + j;
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