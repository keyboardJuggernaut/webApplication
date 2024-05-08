package it.polimi.parkingService.webApplication.analysis.builder;

import java.util.Map;

public interface Chart {
    public Map<String, Double> getChartDoubleData();
    public Map<String, Integer> getChartIntegerData();

}
