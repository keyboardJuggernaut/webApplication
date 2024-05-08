package it.polimi.parkingService.webApplication;

import it.polimi.parkingService.webApplication.analysis.DataAnalyzer;
import it.polimi.parkingService.webApplication.messaging.models.Forum;
import it.polimi.parkingService.webApplication.messaging.services.IForumService;
import it.polimi.parkingService.webApplication.parking.models.ParkingArea;
import it.polimi.parkingService.webApplication.parking.models.ParkingSpot;
import it.polimi.parkingService.webApplication.parking.enums.StripeColor;
import it.polimi.parkingService.webApplication.parking.services.IParkingAreaService;
import it.polimi.parkingService.webApplication.payment.services.IPaymentReceiptService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Map;


@SpringBootApplication
public class WebApplication {

	@Value("${parking.area.order}")
	private int ORDER;

	@Value("${parking.area.name}")
	private String PARKING_AREA_NAME;

	@Value("${review.forum.name}")
	private String REVIEWS_FORUM_NAME;

	@Value("${reporting.forum.name}")
	private String REPORTING_FORUM_NAME;

	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(IParkingAreaService parkingAreaService, IForumService forumService, IPaymentReceiptService paymentReceiptService) {

		return runner -> {
			initApplication(parkingAreaService, forumService);
//			checkBuilder(paymentReceiptService);
		};
	}

	private void initApplication(IParkingAreaService parkingAreaService, IForumService forumService)  {

		// init parking area
		ParkingArea parkingArea = parkingAreaService.findById(1);
		if(parkingArea == null) {
			parkingArea = new ParkingArea(PARKING_AREA_NAME, ORDER);
			for (int i = 0; i < ORDER; i++) {
				for (int j = 0; j < ORDER; j++) {
					ParkingSpot parkingSpot = new ParkingSpot(i, j);
					if (j == 3) {
						parkingSpot.setStripeColor(StripeColor.YELLOW);
					}
					if (i == 4 && j == 4) {
						parkingSpot.setStripeColor(StripeColor.PINK);
					}
					parkingArea.setParkingSpot(parkingSpot);
				}
			}
			parkingAreaService.save(parkingArea);
		}

		// init forums
		if(forumService.findById(REVIEWS_FORUM_NAME) == null) {
			Forum reviewForum = new Forum(REVIEWS_FORUM_NAME);
			forumService.save(reviewForum);

		}

	}

//	private void checkBuilder(IPaymentReceiptService paymentReceiptService) {
//		DataAnalyzer analyzer = new DataAnalyzer(paymentReceiptService);
//		Map<String, Double> result =  analyzer.getPeriodicIncome();
//		System.out.println(result);
//	}

}
