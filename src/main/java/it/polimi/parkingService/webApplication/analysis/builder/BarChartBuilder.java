package it.polimi.parkingService.webApplication.analysis.builder;

import it.polimi.parkingService.webApplication.payment.services.IPaymentReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class BarChartBuilder extends DataChartBuilder {


    private IPaymentReceiptService paymentReceiptService;
    private BarChart graphData;

    public BarChartBuilder(IPaymentReceiptService paymentReceiptService) {
        this.paymentReceiptService = paymentReceiptService;
    }

    @Override
    public void reset() {
        graphData = new BarChart();
    }

    @Override
    public void setPeriods(int number) {
        graphData.setPeriods(number);
    }


    @Override
    public void getData() {
        Map<String, List<Double>> amountsPerDay = new TreeMap<>();
        for (int i = graphData.getPeriods() -1 ; i >= 0 ; i--) {
            LocalDate date = LocalDate.now().minusDays(i);
            List<Double> amounts = paymentReceiptService.findAmountByDate(date, date.plusDays(1));
            amountsPerDay.put(date.toString(), amounts);
        }
        graphData.setRawData(amountsPerDay);
    }

    @Override
    public void processData(String operation) {
        Map<String, List<Double>> dailyValues = graphData.getRawData();
        Map<String, Double> dailySumValues = new TreeMap<>();

        switch (operation) {
            case "average":
                //implement...
                break;
            default:
                for (Map.Entry<String, List<Double>> entry : dailyValues.entrySet()) {
                    String day = entry.getKey();
                    Double[] values = entry.getValue().toArray(new Double[0]);

                    double sum = .0;
                    for (Double value : values) {
                        sum += value;
                    }
                    dailySumValues.put(day, sum);
                }
        }

        graphData.setChartData(dailySumValues);
    }

    public Chart getResult(){
        BarChart data = this.graphData;
        reset();
        return data;
    }
}
