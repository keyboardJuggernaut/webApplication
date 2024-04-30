package it.polimi.parkingService.webApplication.analysis.builder;

import java.util.List;
import java.util.Map;

public abstract class DataChartBuilder {

    public abstract void reset();
    public abstract void setPeriods(int number);
    public abstract void getData();
    public abstract void processData(String operation);

    public abstract Map<String, Double> getResult();

}
