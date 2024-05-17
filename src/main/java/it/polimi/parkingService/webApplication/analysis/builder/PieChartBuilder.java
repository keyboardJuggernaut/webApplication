package it.polimi.parkingService.webApplication.analysis.builder;

import it.polimi.parkingService.webApplication.parking.services.IBookingService;

import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;

/**
 * The {@code PieChartBuilder} builds pie chart model
 */
public class PieChartBuilder extends  DataChartBuilder{

    PieChart pieChart;
    private final IBookingService bookingService;

    public PieChartBuilder(IBookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Override
    public void reset() {
        pieChart = new PieChart();
    }

    @Override
    public void setPeriods(int number) {
        // number of following day to inspect
        pieChart.setPeriods(number);
    }

    /**
     * Retrieves data to visualize
     */
    @Override
    public void getData() {
        Map<String, Integer> bookingsperDay = new TreeMap<>();
        for (int i = 0 ; i < pieChart.getPeriods() ; i++) {
            LocalDate date = LocalDate.now().plusDays(i);
            Integer bookings = bookingService.countBookingByDate(date);
            bookingsperDay.put(date.toString(), bookings);
        }
        pieChart.setChartData(bookingsperDay);
    }

    /**
     * Process data to visualize
     * @param operation the operation to do
     */
    @Override
    public void processData(String operation) {
        // do nothing
    }

    @Override
    public Chart getResult() {
        PieChart data = this.pieChart;
        reset();
        return data;
    }
}
