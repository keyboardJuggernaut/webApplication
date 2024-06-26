package it.polimi.parkingService.webApplication.parking.models;

import it.polimi.parkingService.webApplication.parking.enums.ParkingSpotStatus;
import it.polimi.parkingService.webApplication.parking.enums.ParkingStripes;
import it.polimi.parkingService.webApplication.common.BaseEntity;
import jakarta.persistence.*;

import java.util.List;

/**
 * The {@code ParkingSpot} represents parking spot model
 */
@Entity
@Table(name="parking_spot")
public class ParkingSpot extends BaseEntity {

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="parking_area_id")
    private ParkingArea parkingArea;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private ParkingSpotStatus status = ParkingSpotStatus.FREE;
    @Column(name="stripe_color")
    @Enumerated(EnumType.STRING)
    private ParkingStripes stripeColor = ParkingStripes.WHITE;
    @Column(name="row_index")
    private int rowNumber;
    @Column(name="column_index")
    private int columnNumber;

    @OneToMany(
            mappedBy = "spot",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    private List<Parking> parkings;

    public ParkingSpot(){}

    public ParkingSpot(int rowNumber, int columnNumber) {
        this.rowNumber = rowNumber;
        this.columnNumber = columnNumber;
    }

    public String getSpotIdentifier() {
        return rowNumber + String.valueOf(columnNumber);
    }

    public ParkingArea getParkingArea() {
        return parkingArea;
    }

    public void setParkingArea(ParkingArea parkingArea) {
        this.parkingArea = parkingArea;
    }

    public ParkingSpotStatus getStatus() {
        return status;
    }

    public void setStatus(ParkingSpotStatus status) {
        this.status = status;
    }

    public ParkingStripes getStripeColor() {
        return stripeColor;
    }

    public void setStripeColor(ParkingStripes stripeColor) {
        this.stripeColor = stripeColor;
    }

    public int getRowNumber() {
        return rowNumber;
    }


    public int getColumnNumber() {
        return columnNumber;
    }


    @Override
    public String toString() {
        return "ParkingSpot{" +
                "parking=" + parkingArea +
                ", status=" + status +
                ", stripeColor=" + stripeColor +
                ", rowNumber=" + rowNumber +
                ", columnNumber=" + columnNumber +
                '}';
    }
}
