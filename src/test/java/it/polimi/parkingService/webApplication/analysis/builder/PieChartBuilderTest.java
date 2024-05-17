package it.polimi.parkingService.webApplication.analysis.builder;

import it.polimi.parkingService.webApplication.parking.services.IBookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PieChartBuilderTest {
    private PieChartBuilder pieChartBuilder;
    private IBookingService bookingService;

    @BeforeEach
    void setUp() {
        bookingService = mock(IBookingService.class);
        pieChartBuilder = new PieChartBuilder(bookingService);
    }

    @Test
    void testReset() {
        pieChartBuilder.reset();
        assertNotNull(pieChartBuilder.pieChart);
    }

//    @Test
//    void testSetPeriods() {
//        int periods = 10;
//        pieChartBuilder.setPeriods(periods);
//        assertEquals(periods, pieChartBuilder.pieChart.getPeriods());
//    }

//    @Test
//    void testGetData() {
//        LocalDate today = LocalDate.now();
//        when(bookingService.countBookingByDate(any(LocalDate.class)))
//                .thenReturn(5);
//
//        pieChartBuilder.setPeriods(3);
//        pieChartBuilder.getData();
//
//        Map<String, Integer> expectedData = new TreeMap<>();
//        expectedData.put(today.toString(), 5);
//
//        assertEquals(expectedData, pieChartBuilder.pieChart.getChartIntegerData());
//    }


    @Test
    void testGetResult() {
        pieChartBuilder.pieChart = new PieChart();
        pieChartBuilder.setPeriods(5);

        Chart result = pieChartBuilder.getResult();

        assertNotNull(pieChartBuilder.pieChart);
        assertNotNull(result);
        assertInstanceOf(PieChart.class, result);
        assertEquals(5, ((PieChart) result).getPeriods());
    }
}
