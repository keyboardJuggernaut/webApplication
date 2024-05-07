package it.polimi.parkingService.webApplication.analysis;

import it.polimi.parkingService.webApplication.analysis.builder.BarChartBuilder;
import it.polimi.parkingService.webApplication.analysis.builder.Chart;
import it.polimi.parkingService.webApplication.analysis.builder.ChartMaker;
import it.polimi.parkingService.webApplication.analysis.builder.DataChartBuilder;
import it.polimi.parkingService.webApplication.payment.services.IPaymentReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DataAnalyzer {
    private IPaymentReceiptService paymentReceiptService;

    @Autowired
    public DataAnalyzer(IPaymentReceiptService paymentReceiptService) {
        this.paymentReceiptService = paymentReceiptService;
    }

    public Map<String, Double> getPeriodicIncome() {
        ChartMaker director = new ChartMaker();
        DataChartBuilder builder = new BarChartBuilder(paymentReceiptService);
        director.makePaymentBarChartData(builder);
        Chart barChar = builder.getResult();
        return barChar.getChartData();
    }
}
