package it.polimi.parkingService.webApplication.analysis;

import it.polimi.parkingService.webApplication.analysis.builder.*;
import it.polimi.parkingService.webApplication.parking.services.IBookingService;
import it.polimi.parkingService.webApplication.payment.services.IPaymentReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * The {@code DataAnalyzer} handles data analysis functionality
 */
@Component
public class DataAnalyzer {
    private final IPaymentReceiptService paymentReceiptService;

    private final IBookingService bookingService;

    private final ChartMaker director;

    /**
     * Constructs the class
     * @param paymentReceiptService the services handling payment receipt business logic
     * @param director the building director
     * @param bookingService the services handling booking business logic
     */
    @Autowired
    public DataAnalyzer(IPaymentReceiptService paymentReceiptService, ChartMaker director, IBookingService bookingService) {
        this.paymentReceiptService = paymentReceiptService;
        this.director = director;
        this.bookingService = bookingService;
    }

    /**
     * Returns periodic income analysis
     * @return chart data
     */
    public Map<String, Double> getPeriodicIncome() {
        DataChartBuilder builder = new BarChartBuilder(paymentReceiptService);
        director.makePaymentBarChart(builder);
        Chart barChar = builder.getResult();
        return barChar.getChartDoubleData();
    }

    /**
     * Returns periodic booking analysis
      * @return chart data
     */
   public Map<String, Integer> getPeriodicBooking() {
       DataChartBuilder builder = new PieChartBuilder(bookingService);
       director.makeBookingPieChart(builder);
       Chart pieChart = builder.getResult();
       return pieChart.getChartIntegerData();
   }
}
