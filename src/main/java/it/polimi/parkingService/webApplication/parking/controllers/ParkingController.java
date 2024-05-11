package it.polimi.parkingService.webApplication.parking.controllers;

import com.google.zxing.WriterException;
import it.polimi.parkingService.webApplication.parking.exceptions.ParkingAlreadyInProgress;
import it.polimi.parkingService.webApplication.parking.exceptions.ParkingNotInProgress;
import it.polimi.parkingService.webApplication.parking.models.Parking;
import it.polimi.parkingService.webApplication.parking.services.IParkingService;
import it.polimi.parkingService.webApplication.common.AuthenticationFacadeUserUnauthenticated;
import it.polimi.parkingService.webApplication.common.IAuthenticationFacade;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * The {@code ParkingController} handles any parking related requests
 */
@Controller
@RequestMapping("/parkings")
public class ParkingController {

    private final IParkingService parkingService;

    private final IAuthenticationFacade authenticationFacade;


    /**
     * Constructs the controller
     * @param parkingService the service handling parking business logic
     * @param authenticationFacade the service handling authentication info retrieval logic
     */
    public ParkingController(IParkingService parkingService, IAuthenticationFacade authenticationFacade) {
        this.parkingService = parkingService;
        this.authenticationFacade = authenticationFacade;
    }


    /**
     * Handle any request for parking check in request
     * @param model the model for qrcode
     * @return view reference
     * @throws AuthenticationFacadeUserUnauthenticated if user is not authenticated
     * @throws IOException if IO fails
     * @throws WriterException if writing fails
     * @throws ParkingAlreadyInProgress if checkin has been already done
     */
    @GetMapping("/checkin")
    public String showCheckIn(Model model) throws AuthenticationFacadeUserUnauthenticated, IOException, WriterException, ParkingAlreadyInProgress {
        String username = authenticationFacade.getAuthenticatedUsername();
        String qrCode = parkingService.getCheckInQRCode(username);
        model.addAttribute("qrcode", qrCode);
        return "parking/checkin";
    }

    /**
     * Handle any request for parking check out request
     * @param model the model for qrcode
     * @return view reference
     * @throws AuthenticationFacadeUserUnauthenticated if user is not authenticated
     * @throws IOException if IO fails
     * @throws WriterException if writing fails
     * @throws ParkingAlreadyInProgress if checkin has not been done yet
     */
    @GetMapping("/checkout")
    public String showCheckOut(Model model) throws IOException, WriterException, AuthenticationFacadeUserUnauthenticated, ParkingNotInProgress {
        String username = authenticationFacade.getAuthenticatedUsername();
        String qrCode = parkingService.getCheckOutQRCode(username);
        model.addAttribute("qrcode", qrCode);
        return "parking/checkout";
    }

    /**
     * Handle any request for setting estimated parking time
     * @param parking the model for parking
     * @return view reference
     */
    @PostMapping("")
    public String setEstimatedTime(@Valid @ModelAttribute("parking") Parking parking) {
        parkingService.update(parking.getId(), parking.getEstimatedTime());
        return "redirect:/spots";
    }

}
