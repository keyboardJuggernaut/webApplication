package it.polimi.parkingService.webApplication.analysis.builder;

import it.polimi.parkingService.webApplication.parking.services.IBookingService;
import it.polimi.parkingService.webApplication.parking.services.IParkingService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PieChartBuilder extends  DataChartBuilder{

    private PieChart pieChart;
    private IBookingService bookingService;

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
