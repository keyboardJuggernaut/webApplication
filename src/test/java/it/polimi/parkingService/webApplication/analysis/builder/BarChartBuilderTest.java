package it.polimi.parkingService.webApplication.analysis.builder;

import it.polimi.parkingService.webApplication.payment.services.IPaymentReceiptService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BarChartBuilderTest {
    private BarChartBuilder barChartBuilder;
    private IPaymentReceiptService paymentReceiptService;

    @BeforeEach
    void setUp() {
        paymentReceiptService = mock(IPaymentReceiptService.class);
        barChartBuilder = new BarChartBuilder(paymentReceiptService);
    }

    @Test
    void testReset() {
        barChartBuilder.reset();
        assertNotNull(barChartBuilder.graphData);
    }



    @Test
    void testProcessDataDefault() {
        Map<String, List<Double>> rawData = new HashMap<>();
        rawData.put("2024-05-01", Arrays.asList(10.0, 20.0));
        rawData.put("2024-05-02", Arrays.asList(15.0, 25.0));

        barChartBuilder.graphData = new BarChart();
        barChartBuilder.graphData.setRawData(rawData);

        barChartBuilder.processData("default");

        Map<String, Double> expectedChartData = new TreeMap<>();
        expectedChartData.put("2024-05-01", 30.0);
        expectedChartData.put("2024-05-02", 40.0);

        assertEquals(expectedChartData, barChartBuilder.graphData.getChartDoubleData());
    }


    @Test
    void testGetResult() {
        barChartBuilder.graphData = new BarChart();
        barChartBuilder.setPeriods(5);

        Chart result = barChartBuilder.getResult();

        assertNotNull(barChartBuilder.graphData);
        assertNotNull(result);
        assertInstanceOf(BarChart.class, result);
        assertEquals(5, ((BarChart) result).getPeriods());
    }
}
