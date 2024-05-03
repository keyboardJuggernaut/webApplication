package it.polimi.parkingService.webApplication.parking.controllers;

import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import it.polimi.parkingService.webApplication.parking.models.ParkingSpot;
import it.polimi.parkingService.webApplication.parking.services.IParkingAreaService;
import it.polimi.parkingService.webApplication.parking.services.IParkingSpotService;
import it.polimi.parkingService.webApplication.utils.QRCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/parkingArea")
public class ParkingAreaController {

    private IParkingAreaService parkingAreaService;
    private IParkingSpotService parkingSpotService;


    @Autowired
    private ParkingAreaController(IParkingAreaService parkingAreaService, IParkingSpotService parkingSpotService){
        this.parkingAreaService = parkingAreaService;
        this.parkingSpotService = parkingSpotService;
    }

    @GetMapping("/map")
    public String showParkingArea(Model model) {
         List<ParkingSpot> parkingSpots= parkingSpotService.findAll();
         model.addAttribute("parkingSpots", parkingSpots);
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

//    @PostMapping("/parking")
//    public String validateCheckInQRCode(@ModelAttribute("qrcode") String qrcode, Model model) throws NotFoundException, IOException {
//        ParkingSpot parkingSpot = parkingAreaService.findParkingSpot(QRCodeGenerator.decodeQRCodeEncodedImage(qrcode));
//    }
}
