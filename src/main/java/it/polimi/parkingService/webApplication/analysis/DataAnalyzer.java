package it.polimi.parkingService.webApplication.analysis;

import it.polimi.parkingService.webApplication.analysis.builder.*;
import it.polimi.parkingService.webApplication.parking.services.IBookingService;
import it.polimi.parkingService.webApplication.payment.services.IPaymentReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DataAnalyzer {
    private IPaymentReceiptService paymentReceiptService;

    private IBookingService bookingService;

    private ChartMaker director;

    @Autowired
    public DataAnalyzer(IPaymentReceiptService paymentReceiptService, ChartMaker director, IBookingService bookingService) {
        this.paymentReceiptService = paymentReceiptService;
        this.director = director;
        this.bookingService = bookingService;
    }

    public Map<String, Double> getPeriodicIncome() {
        DataChartBuilder builder = new BarChartBuilder(paymentReceiptService);
        director.makePaymentBarChart(builder);
        Chart barChar = builder.getResult();
        return barChar.getChartDoubleData();
    }

   public Map<String, Integer> getPeriodicBooking() {
       DataChartBuilder builder = new PieChartBuilder(bookingService);
       director.makeBookingPieChart(builder);
       Chart pieChart = builder.getResult();
       return pieChart.getChartIntegerData();
   }
}
