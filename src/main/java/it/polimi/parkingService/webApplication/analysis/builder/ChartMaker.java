package it.polimi.parkingService.webApplication.analysis.builder;


import org.springframework.stereotype.Component;

@Component
public class ChartMaker {
    public void makePaymentBarChart(DataChartBuilder builder) {
        builder.reset();
        builder.setPeriods(7); // days
        builder.getData(); // daily payments
        builder.processData("sum");
    }

    public void  makeBookingPieChart(DataChartBuilder builder) {
        builder.reset();
        builder.setPeriods(7);
        builder.getData();
    }
}
