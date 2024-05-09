package it.polimi.parkingService.webApplication;


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


@SpringBootApplication
public class WebApplication {

	@Value("${parking.area.order}")
	private int ORDER;

	@Value("${parking.area.name}")
	private String PARKING_AREA_NAME;


	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(IParkingAreaService parkingAreaService, IPaymentReceiptService paymentReceiptService) {

		return runner -> {
			initApplication(parkingAreaService);
//			checkBuilder(paymentReceiptService);
		};
	}

	private void initApplication(IParkingAreaService parkingAreaService)  {

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

	}

}
