package it.polimi.parkingService.webApplication.analysis.builder;

import java.util.Map;

public interface Chart {

    Map<String, Double> getChartDoubleData();
    Map<String, Integer> getChartIntegerData();

}
