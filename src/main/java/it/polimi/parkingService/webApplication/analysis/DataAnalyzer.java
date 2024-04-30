package it.polimi.parkingService.webApplication.analysis;

import it.polimi.parkingService.webApplication.analysis.builder.BarChartBuilder;
import it.polimi.parkingService.webApplication.analysis.builder.ChartMaker;
import it.polimi.parkingService.webApplication.analysis.builder.DataChartBuilder;

import java.util.Map;

public class DataAnalyzer {

    public Map<String, Double> getPeriodicIncome() {
        ChartMaker director = new ChartMaker();
        DataChartBuilder builder = new BarChartBuilder();
        director.makePaymentBarChartData(builder);
        return builder.getResult();
    }
}
