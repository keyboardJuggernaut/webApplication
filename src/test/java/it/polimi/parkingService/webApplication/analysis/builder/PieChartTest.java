package it.polimi.parkingService.webApplication.analysis.builder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PieChartTest {
    private PieChart pieChart;

    @BeforeEach
    void setUp() {
        pieChart = new PieChart();
    }

    @Test
    void testSetAndGetRawData() {
        Map<String, Integer> rawData = new HashMap<>();
        rawData.put("Category1", 10);
        rawData.put("Category2", 20);

        pieChart.setRawData(rawData);

        assertEquals(rawData, pieChart.getRawData());
    }

    @Test
    void testSetAndGetPeriods() {
        Integer periods = 5;
        pieChart.setPeriods(periods);
        assertEquals(periods, pieChart.getPeriods());
    }

    @Test
    void testSetAndGetChartData() {
        Map<String, Integer> chartData = new HashMap<>();
        chartData.put("Category1", 25);
        chartData.put("Category2", 35);

        pieChart.setChartData(chartData);

        assertEquals(chartData, pieChart.getChartIntegerData());
    }

    @Test
    void testGetChartDoubleData() {
        assertNull(pieChart.getChartDoubleData());
    }
}
