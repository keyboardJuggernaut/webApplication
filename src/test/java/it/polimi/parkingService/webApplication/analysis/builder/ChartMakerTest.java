package it.polimi.parkingService.webApplication.analysis.builder;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.*;

@SpringBootTest
public class ChartMakerTest {
    @Mock
    private DataChartBuilder dataChartBuilder;

    @InjectMocks
    private ChartMaker chartMaker;

    @Test
    public void testMakePaymentBarChart() {
        reset(dataChartBuilder);

        chartMaker.makePaymentBarChart(dataChartBuilder);

        verify(dataChartBuilder).reset();
        verify(dataChartBuilder).setPeriods(7);
        verify(dataChartBuilder).getData();
        verify(dataChartBuilder).processData("sum");

        verifyNoMoreInteractions(dataChartBuilder);
    }

    @Test
    public void testMakeBookingPieChart() {
        reset(dataChartBuilder);

        chartMaker.makeBookingPieChart(dataChartBuilder);

        verify(dataChartBuilder).reset();
        verify(dataChartBuilder).setPeriods(7);
        verify(dataChartBuilder).getData();

        verifyNoMoreInteractions(dataChartBuilder);
    }
}
