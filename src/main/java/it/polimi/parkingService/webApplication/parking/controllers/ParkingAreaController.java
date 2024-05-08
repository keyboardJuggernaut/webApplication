package it.polimi.parkingService.webApplication.parking.controllers;

import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import it.polimi.parkingService.webApplication.account.models.User;
import it.polimi.parkingService.webApplication.parking.enums.ParkingSpotStatus;
import it.polimi.parkingService.webApplication.parking.exceptions.ParkingNotTerminated;
import it.polimi.parkingService.webApplication.parking.models.Booking;
import it.polimi.parkingService.webApplication.parking.models.Parking;
import it.polimi.parkingService.webApplication.parking.models.ParkingSpot;
import it.polimi.parkingService.webApplication.parking.services.*;
import it.polimi.parkingService.webApplication.payment.models.PaymentReceipt;
import it.polimi.parkingService.webApplication.utils.QRCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@Controller
@RequestMapping("/parkingArea")
public class ParkingAreaController {

    private IParkingAreaService parkingAreaService;
    private IParkingService parkingService;

    private SseService sseService;


    @Autowired
    private ParkingAreaController(IParkingAreaService parkingAreaService, IParkingService parkingService, SseService sseService){
        this.parkingAreaService = parkingAreaService;
        this.parkingService = parkingService;
        this.sseService = sseService;
    }

    @GetMapping("/map")
    public String showParkingArea(Model model) {
         Map<ParkingSpot, Parking> spotWithParking = parkingAreaService.getSpotsWithParkings();
         model.addAttribute("spotWithParking", spotWithParking);
         return "parkingArea/map";
    }

    @GetMapping("/checkin")
    public String showCheckInQRCode(Model model) throws IOException, WriterException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalUsername = authentication.getName();
        String qrCode = parkingAreaService.getCheckInQRCode(currentPrincipalUsername);
        model.addAttribute("qrcode", qrCode);
        return "parkingArea/checkin";
    }

    @GetMapping("/checkout")
    public String showCheckOutQRCode(Model model) throws IOException, WriterException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalUsername = authentication.getName();
        String qrCode = parkingAreaService.getCheckOutQRCode(currentPrincipalUsername);
        model.addAttribute("qrcode", qrCode);
        return "parkingArea/checkout";
    }

    @GetMapping("/parking")
    public String validateCheckInQRCode(@RequestParam("qrcode") String qrcode, Model model) throws NotFoundException, IOException {
        ParkingSpot parkingSpot = parkingAreaService.findParkingSpot(QRCodeGenerator.decodeQRCodeEncodedImage(qrcode));
        model.addAttribute("spotIdentifier", parkingSpot.getSpotIdentifier());
        Parking parking = parkingService.findActualInProgressParkingBySpot(parkingSpot);
        model.addAttribute("parking", parking);
        return "parkingArea/parking-confirmation";
    }
    @GetMapping("/leaving")
    public String validateCheckOutQRCode(@RequestParam("qrcode") String qrcode, Model model) throws NotFoundException, IOException, ParkingNotTerminated {

        PaymentReceipt receipt = parkingAreaService.doCheckout(QRCodeGenerator.decodeQRCodeEncodedImage(qrcode));
        model.addAttribute("receipt", receipt);
        return "parkingArea/checkout-confirmation";
    }


    @PostMapping("/estimatedTime")
    public String setEstimatedTime(@ModelAttribute("parking") Parking parking) {
        if(parking.getEstimatedTime() == null) {
            throw new RuntimeException("Missing estimated time");
        }
        parkingService.update(parking.getId(), parking.getEstimatedTime());
       return "redirect:/parkingArea/map";
    }

    @GetMapping(path = "/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe() {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        sseService.addEmitter(emitter);
        return emitter;
    }

}
