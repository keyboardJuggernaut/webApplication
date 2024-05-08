package it.polimi.parkingService.webApplication.analysis.builder;


public class ChartMaker {
    public void makePaymentBarChart(DataChartBuilder builder) {
        builder.reset();
        builder.setPeriods(7); // days
        builder.getData(); // daily payments
        builder.processData("sum");
    }

    public void  makeParkingPieChart(DataChartBuilder builder) {

    }
}
