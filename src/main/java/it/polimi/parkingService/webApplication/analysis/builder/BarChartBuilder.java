package it.polimi.parkingService.webApplication.analysis.builder;

import java.util.Map;
import java.util.TreeMap;

public class BarChartBuilder extends DataChartBuilder {
    private BarChartGraphData graphData;
    @Override
    public void reset() {
        graphData = new BarChartGraphData();
    }

    @Override
    public void setPeriods(int number) {
        graphData.setPeriods(number);
    }


    @Override
    public void getData() {
        // query payment and convert to a map<day, amounts[]>
        graphData.setRawData(new TreeMap<>());
    }

    @Override
    public void processData(String operation) {
        Map<String, Double[]> dailyValues = graphData.getRawData();
        Map<String, Double> dailySumValues = new TreeMap<>();

        switch (operation) {
            case "average":
                //implement...
                break;
            default:
                for (Map.Entry<String, Double[]> entry : dailyValues.entrySet()) {
                    String day = entry.getKey();
                    Double[] values = entry.getValue();

                    double sum = .0;
                    for (Double value : values) {
                        sum += value;
                    }
                    dailySumValues.put(day, sum);
                }
        }

        graphData.setChartData(dailySumValues);
    }

    public Map<String, Double> getResult(){
        BarChartGraphData data = this.graphData;
        reset();
        return data.getChartData();
    }
}
