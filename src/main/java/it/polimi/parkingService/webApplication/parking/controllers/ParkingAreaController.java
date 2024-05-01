package it.polimi.parkingService.webApplication.parking.controllers;

import it.polimi.parkingService.webApplication.parking.models.ParkingSpot;
import it.polimi.parkingService.webApplication.parking.services.IParkingAreaService;
import it.polimi.parkingService.webApplication.parking.services.IParkingSpotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
