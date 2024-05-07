package it.polimi.parkingService.webApplication.analysis.builder;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class BarChart implements Chart {
    private Map<String, Double> chartData;

    private Integer periods;

    private Map<String, List<Double>> rawData;

    public BarChart() {
        this.chartData = new TreeMap<>();
    }

    public Map<String, List<Double>> getRawData() {
        return rawData;
    }

    public void setRawData(Map<String, List<Double>> rawData) {
        this.rawData = rawData;
    }

    public Map<String, Double> getChartData() {

        return chartData;
    }

    public Integer getPeriods() {
        return periods;
    }

    public void setPeriods(Integer periods) {
        this.periods = periods;
    }

    public void setChartData(Map<String, Double> chartData) {
        this.chartData = chartData;
    }
}
