package it.polimi.parkingService.webApplication;

import it.polimi.parkingService.webApplication.messaging.models.Forum;
import it.polimi.parkingService.webApplication.messaging.services.IForumService;
import it.polimi.parkingService.webApplication.parking.models.ParkingArea;
import it.polimi.parkingService.webApplication.parking.models.ParkingSpot;
import it.polimi.parkingService.webApplication.parking.enums.StripeColor;
import it.polimi.parkingService.webApplication.parking.services.IParkingAreaService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;


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
	public CommandLineRunner commandLineRunner(IParkingAreaService parkingAreaService, IForumService forumService) {

		return runner -> {
			initApplication(parkingAreaService, forumService);
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
		if(forumService.findById(REPORTING_FORUM_NAME) == null) {
			Forum reportingForum = new Forum(REPORTING_FORUM_NAME);

			forumService.save(reportingForum);
		}
	}



}
