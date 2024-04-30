package it.polimi.parkingService.webApplication.parking.models;

import it.polimi.parkingService.webApplication.parking.enums.ParkingSpotStatus;
import it.polimi.parkingService.webApplication.parking.enums.StripeColor;

import java.util.ArrayList;
import java.util.List;

public class ParkingSpot {

    private ParkingArea parkingArea;
    private ParkingSpotStatus status = ParkingSpotStatus.FREE;
    private StripeColor stripeColor = StripeColor.WHITE;

    private int rowNumber;
    private int columnNumber;

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
