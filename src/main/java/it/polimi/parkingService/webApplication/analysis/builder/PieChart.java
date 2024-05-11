package it.polimi.parkingService.webApplication.analysis.builder;

import java.util.Map;
import java.util.TreeMap;

/**
 * The {@code PieChart} defines pie chart model
 */
public class PieChart implements Chart {

    private Map<String, Integer> chartData;

    private Integer periods;

    private Map<String, Integer> rawData;

    public PieChart() {
        this.chartData = new TreeMap<>();
    }

    public Map<String, Integer> getRawData() {
        return rawData;
    }

    public void setRawData(Map<String, Integer> rawData) {
        this.rawData = rawData;
    }

    public Map<String, Double> getChartDoubleData() {

        return null;
    }

    @Override
    public Map<String, Integer> getChartIntegerData() {
        return chartData;
    }

    public Integer getPeriods() {
        return periods;
    }

    public void setPeriods(Integer periods) {
        this.periods = periods;
    }

    public void setChartData(Map<String, Integer> chartData) {
        this.chartData = chartData;
    }
}
