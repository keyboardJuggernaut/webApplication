package it.polimi.parkingService.webApplication.analysis;

import it.polimi.parkingService.webApplication.analysis.builder.*;
import it.polimi.parkingService.webApplication.parking.services.IBookingService;
import it.polimi.parkingService.webApplication.payment.services.IPaymentReceiptService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class DataAnalyzerTest {
    @Mock
    private IPaymentReceiptService paymentReceiptService;

    @Mock
    private IBookingService bookingService;

    @Mock
    private ChartMaker director;

//    @Test
//    public void testGetPeriodicIncome() {
//        DataChartBuilder builder = new BarChartBuilder(paymentReceiptService);
//        Chart chart = new BarChart();
//        when(director.makePaymentBarChart(builder)).thenReturn(builder);
//        when(builder.getResult()).thenReturn(chart);
//
//        DataAnalyzer dataAnalyzer = new DataAnalyzer(paymentReceiptService, director, bookingService);
//        Map<String, Double> result = dataAnalyzer.getPeriodicIncome();
//
//        assertNotNull(result);
//        assertTrue(result.isEmpty());
//    }
//
//    @Test
//    public void testGetPeriodicBooking() {
//        DataChartBuilder builder = new PieChartBuilder(bookingService);
//        Chart chart = new PieChart();
//        when(director.makeBookingPieChart(builder)).thenReturn(builder);
//        when(builder.getResult()).thenReturn(chart);
//
//        DataAnalyzer dataAnalyzer = new DataAnalyzer(paymentReceiptService, director, bookingService);
//        Map<String, Integer> result = dataAnalyzer.getPeriodicBooking();
//
//        assertNotNull(result);
//        assertTrue(result.isEmpty());
//    }
}
