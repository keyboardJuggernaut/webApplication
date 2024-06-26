package it.polimi.parkingService.webApplication.analysis.builder;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * The {@code BarChart} defines bar chart model
 */
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

    public Map<String, Double> getChartDoubleData() {

        return chartData;
    }

    @Override
    public Map<String, Integer> getChartIntegerData() {
        return null;
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
