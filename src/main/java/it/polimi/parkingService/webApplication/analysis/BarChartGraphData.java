package it.polimi.parkingService.webApplication.analysis;

import java.util.Map;
import java.util.TreeMap;

public class BarChartGraphData {
    private Map<String, Double> chartData;

    private Integer periods;

    private Map<String, Double[]> rawData;

    public BarChartGraphData() {
        this.chartData = new TreeMap<>();
    }

    public Map<String, Double[]> getRawData() {
        return rawData;
    }

    public void setRawData(Map<String, Double[]> rawData) {
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
