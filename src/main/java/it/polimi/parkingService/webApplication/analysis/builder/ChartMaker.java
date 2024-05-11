package it.polimi.parkingService.webApplication.analysis.builder;


import org.springframework.stereotype.Component;

/**
 * The {@code ChartMaker} manages object builders for analysis functionalities
 */
@Component
public class ChartMaker {

    /**
     * Builds payment bar chart
     * @param builder chart builder
     */
    public void makePaymentBarChart(DataChartBuilder builder) {
        builder.reset();
        builder.setPeriods(7); // days
        builder.getData(); // daily payments
        builder.processData("sum");
    }

    /**
     * Builds booking pie chart
     * @param builder chart builder
     */
    public void  makeBookingPieChart(DataChartBuilder builder) {
        builder.reset();
        builder.setPeriods(7);
        builder.getData();
    }
}
