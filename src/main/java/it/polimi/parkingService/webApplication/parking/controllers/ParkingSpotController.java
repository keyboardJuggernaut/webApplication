package it.polimi.parkingService.webApplication.parking.controllers;

import com.google.zxing.NotFoundException;
import it.polimi.parkingService.webApplication.parking.exceptions.ParkingNotTerminated;
import it.polimi.parkingService.webApplication.parking.exceptions.SearchStrategyUndefined;
import it.polimi.parkingService.webApplication.parking.models.Parking;
import it.polimi.parkingService.webApplication.parking.models.ParkingSpot;
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

    /**
     * Constructs the controller
     * @param parkingSpotService the service handling parking spot business logic
     */
    @Autowired
    public ParkingSpotController(IParkingSpotService parkingSpotService) {
        this.parkingSpotService = parkingSpotService;
    }

    /**
     * Handle any request for showing parking area
     * @param model component
     * @return view reference
     */
    @GetMapping("")
    public String showSpots(Model model) {
        Map<ParkingSpot, Parking> parkingArea = parkingSpotService.getSpotsWithParkings();
        model.addAttribute("parkingArea", parkingArea);
        return "parking/map";
    }

    /**
     * Handle any request for starting a parking
     * @param qrcode the qrcode to authorize
     * @param model component
     * @return view reference
     * @throws NotFoundException if user is not present
     * @throws IOException if IO fails
     * @throws SearchStrategyUndefined if search spot strategy is undefined
     */
    @PostMapping("/checkin")
    public String doCheckIn(@RequestParam("qrcode") String qrcode, Model model) throws NotFoundException, IOException, SearchStrategyUndefined {
        Parking parking = parkingSpotService.startParking(QRCodeGenerator.decodeQRCodeEncodedImage(qrcode));
        model.addAttribute("parking", parking);
        return "parking/parking-confirmation";
    }

    /**
     * Handle any request for terminating a parking
     * @param qrcode the qrcode to authorize
     * @param model component
     * @return view reference
     * @throws NotFoundException if user is not present
     * @throws IOException if IO fails
     * @throws ParkingNotTerminated if parking leaving time is undefined
     */
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
