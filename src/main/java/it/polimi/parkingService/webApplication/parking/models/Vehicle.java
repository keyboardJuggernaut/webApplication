package it.polimi.parkingService.webApplication.parking.models;

public interface Vehicle {
    String getLicensePlate();

    void setLicensePlate(String licensePlate);

    String getMake();

    void setMake(String make);

    String getModel();

    void setModel(String model);

    String getColor();

    void setColor(String color);

    String getType();

    void setType(String type);
}
