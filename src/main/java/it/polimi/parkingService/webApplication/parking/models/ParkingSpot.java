package it.polimi.parkingService.webApplication.parking.models;

import it.polimi.parkingService.webApplication.parking.enums.ParkingSpotStatus;
import it.polimi.parkingService.webApplication.parking.enums.StripeColor;
import it.polimi.parkingService.webApplication.utils.BaseEntity;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

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
    private StripeColor stripeColor = StripeColor.WHITE;
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
        return String.valueOf(rowNumber) + String.valueOf(columnNumber);
    }

    public void addParking(Parking parking){
        if(parkings == null) {
            parkings = new ArrayList<>();
        }
        parkings.add(parking);
        parking.setSpot(this);
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

    public StripeColor getStripeColor() {
        return stripeColor;
    }

    public void setStripeColor(StripeColor stripeColor) {
        this.stripeColor = stripeColor;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public int getColumnNumber() {
        return columnNumber;
    }

    public void setColumnNumber(int columnNumber) {
        this.columnNumber = columnNumber;
    }

    public List<Parking> getParkings() {
        return parkings;
    }

    public void setParkings(List<Parking> parkings) {
        this.parkings = parkings;
    }

    @Override
    public String toString() {
        return "ParkingSpot{" +
                "parkingArea=" + parkingArea +
                ", status=" + status +
                ", stripeColor=" + stripeColor +
                ", rowNumber=" + rowNumber +
                ", columnNumber=" + columnNumber +
                '}';
    }
}
