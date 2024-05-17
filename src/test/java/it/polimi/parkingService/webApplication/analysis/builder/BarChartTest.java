package it.polimi.parkingService.webApplication.analysis.builder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class BarChartTest {
    private BarChart barChart;

    @BeforeEach
    void setUp() {
        barChart = new BarChart();
    }

    @Test
    void testSetAndGetRawData() {
        Map<String, List<Double>> rawData = new HashMap<>();
        rawData.put("Category1", Arrays.asList(10.0, 20.0, 30.0));
        rawData.put("Category2", Arrays.asList(15.0, 25.0, 35.0));

        barChart.setRawData(rawData);

        assertEquals(rawData, barChart.getRawData());
    }

    @Test
    void testSetAndGetPeriods() {
        Integer periods = 5;
        barChart.setPeriods(periods);
        assertEquals(periods, barChart.getPeriods());
    }

    @Test
    void testSetAndGetChartData() {
        Map<String, Double> chartData = new HashMap<>();
        chartData.put("Category1", 25.0);
        chartData.put("Category2", 35.0);

        barChart.setChartData(chartData);

        assertEquals(chartData, barChart.getChartDoubleData());
    }

    @Test
    void testGetChartIntegerData() {
        assertNull(barChart.getChartIntegerData());
    }
}
