package it.polimi.parkingService.webApplication.analysis.builder;


public class ChartMaker {
    public void makePaymentBarChartData(DataChartBuilder builder) {
        builder.reset();
        builder.setPeriods(7); // days
        builder.getData(); // daily payments
        builder.processData("sum");
    }

    public void  makeParkingPieChartData(DataChartBuilder builder) {

    }
}
