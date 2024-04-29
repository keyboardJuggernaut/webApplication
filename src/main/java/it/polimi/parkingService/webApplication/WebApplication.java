package it.polimi.parkingService.webApplication;

import it.polimi.parkingService.webApplication.parking.enums.ParkingSpotStatus;
import it.polimi.parkingService.webApplication.parking.models.ParkingArea;
import it.polimi.parkingService.webApplication.parking.models.ParkingSpot;
import it.polimi.parkingService.webApplication.parking.enums.StripeColor;
import it.polimi.parkingService.webApplication.parking.utils.ParkingSpotSearchCriteria;
import it.polimi.parkingService.webApplication.parking.utils.SearchCriteria;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WebApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner() {

		return runner -> {

			initApplication();
		};
	}

	private void initApplication() {

		final int ORDER = 5;

		ParkingArea parkingArea = new ParkingArea("Parcheggio del centro", ORDER);
		for (int i = 0; i < ORDER; i++) {
			for (int j = 0; j < ORDER; j++) {
				ParkingSpot parkingSpot = new ParkingSpot(parkingArea, i, j);
				if(j % 3 == 0) {
					parkingSpot.setStripeColor(StripeColor.YELLOW);
				}
				if(i == 0 && j == 0) {
					parkingSpot.setStripeColor(StripeColor.PINK);
				}
				parkingArea.setParkingSpot(parkingSpot);
			}
		}

		SearchCriteria criteria = new ParkingSpotSearchCriteria(ParkingSpotStatus.FREE, StripeColor.YELLOW);
		ParkingSpot parkingSpot = parkingArea.searchForParkingSpot(criteria);
		System.out.println(parkingSpot);
	}

}
