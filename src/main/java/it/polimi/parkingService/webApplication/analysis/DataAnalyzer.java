package it.polimi.parkingService.webApplication.analysis;

import java.util.Map;

public class DataAnalyzer {

    public Map<String, Double> getPeriodicIncome() {
        ChartMaker director = new ChartMaker();
        DataChartBuilder builder = new BarChartBuilder();
        director.makePaymentBarChartData(builder);
        return builder.getResult();
    }
}
