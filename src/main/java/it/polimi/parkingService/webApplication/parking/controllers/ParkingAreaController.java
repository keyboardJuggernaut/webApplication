package it.polimi.parkingService.webApplication.parking.controllers;

import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import it.polimi.parkingService.webApplication.parking.models.Parking;
import it.polimi.parkingService.webApplication.parking.models.ParkingSpot;
import it.polimi.parkingService.webApplication.parking.services.IParkingAreaService;
import it.polimi.parkingService.webApplication.parking.services.IParkingService;
import it.polimi.parkingService.webApplication.parking.services.IParkingSpotService;
import it.polimi.parkingService.webApplication.parking.services.SseService;
import it.polimi.parkingService.webApplication.utils.QRCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@Controller
@RequestMapping("/parkingArea")
public class ParkingAreaController {

    private IParkingAreaService parkingAreaService;
    private IParkingSpotService parkingSpotService;
    private IParkingService parkingService;

    private SseService sseService;

    private final Set<SseEmitter> emitters = new CopyOnWriteArraySet<>();



    @Autowired
    private ParkingAreaController(IParkingAreaService parkingAreaService, IParkingSpotService parkingSpotService, IParkingService parkingService, SseService sseService){
        this.parkingAreaService = parkingAreaService;
        this.parkingSpotService = parkingSpotService;
        this.parkingService = parkingService;
        this.sseService = sseService;
    }

    @GetMapping("/map")
    public String showParkingArea(Model model) {
         Map<ParkingSpot, Parking> spotWithParking = parkingSpotService.getSpotsWithParkings();
         model.addAttribute("spotWithParking", spotWithParking);
         return "parkingArea/map";
    }

    @GetMapping("/checkin")
    public String showCheckInQRCode(Model model) throws IOException, WriterException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalUsername = authentication.getName();
        String qrCode = parkingAreaService.getCheckInQRCode(currentPrincipalUsername);
        model.addAttribute("qrcode", qrCode);
        for (SseEmitter emitter : emitters) {
            emitter.send("test");
        }
        return "parkingArea/checkin";
    }

    @GetMapping("/parking")
    public String validateCheckInQRCode(@RequestParam("qrcode") String qrcode, Model model) throws NotFoundException, IOException {
        ParkingSpot parkingSpot = parkingAreaService.findParkingSpot(QRCodeGenerator.decodeQRCodeEncodedImage(qrcode));
        model.addAttribute("spotIdentifier", parkingSpot.getSpotIdentifier());
        Parking parking = parkingService.findBySpotEquals(parkingSpot);
        model.addAttribute("parking", parking);
        return "parkingArea/parking-confirmation";
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
