package it.polimi.parkingService.webApplication.parking.controllers;

import com.google.zxing.NotFoundException;
import it.polimi.parkingService.webApplication.parking.exceptions.ParkingNotTerminated;
import it.polimi.parkingService.webApplication.parking.models.Parking;
import it.polimi.parkingService.webApplication.parking.models.ParkingSpot;
import it.polimi.parkingService.webApplication.parking.services.IParkingService;
import it.polimi.parkingService.webApplication.parking.services.IParkingSpotService;
import it.polimi.parkingService.webApplication.payment.models.PaymentReceipt;
import it.polimi.parkingService.webApplication.utils.QRCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

/**
 * The {@code ParkingSpotController} handles any parking spot related requests
 */
@Controller
@RequestMapping("/spots")
public class ParkingSpotController {

    private final IParkingSpotService parkingSpotService;
    private final IParkingService parkingService;

    /**
     * Constructs the controller
     * @param parkingSpotService the service handling parking spot business logic
     * @param parkingService the service handling parking business logic
     */
    @Autowired
    public ParkingSpotController(IParkingSpotService parkingSpotService,IParkingService parkingService) {
        this.parkingSpotService = parkingSpotService;
        this.parkingService = parkingService;
    }

    /**
     * Handle any request for showing parking area
     * @param model component
     * @return view reference
     */
    @GetMapping("")
    public String showParkingArea(Model model) {
        Map<ParkingSpot, Parking> parkingArea = parkingSpotService.getSpotsWithParkings();
        model.addAttribute("parkingArea", parkingArea);
        return "parking/map";
    }

    // TODO: comment after refactoring service related
    @PostMapping("/checkin")
    public String doCheckIn(@RequestParam("qrcode") String qrcode, Model model) throws NotFoundException, IOException {
        ParkingSpot parkingSpot = parkingSpotService.findParkingSpot(QRCodeGenerator.decodeQRCodeEncodedImage(qrcode));

        Parking parking = parkingService.findActualInProgressParkingBySpot(parkingSpot);

        model.addAttribute("spotIdentifier", parkingSpot.getSpotIdentifier());
        model.addAttribute("parking", parking);
        return "parking/parking-confirmation";
    }

    // TODO: comment after refactoring service related
    @PostMapping("/checkout")
    public String doCheckout(@RequestParam("qrcode") String qrcode, Model model) throws NotFoundException, IOException, ParkingNotTerminated {

        PaymentReceipt receipt = parkingSpotService.doCheckout(QRCodeGenerator.decodeQRCodeEncodedImage(qrcode));
        model.addAttribute("receipt", receipt);
        return "parking/checkout-confirmation";
    }

    /**
     * Handle any request for toggling parking spot availability
     * @param id parking spot id
     * @return redirecting reference
     */
    @PostMapping("/{id}/toggleAvailability")
    public String toggleAvailability(@PathVariable long id) {
        parkingSpotService.toggleSpotAvailability(id);
        return "redirect:/spots";
    }
}
