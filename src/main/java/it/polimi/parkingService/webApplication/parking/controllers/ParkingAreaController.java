package it.polimi.parkingService.webApplication.parking.controllers;

import it.polimi.parkingService.webApplication.parking.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * The {@code ParkingAreaController} handles any parking area related requests
 */
@Controller
@RequestMapping("/parkingArea")
public class ParkingAreaController {

    private final SseService sseService;

    /**
     * Constructs the controller
     * @param sseService the service handling server-sent events logic
     */
    @Autowired
    private ParkingAreaController(SseService sseService){
        this.sseService = sseService;
    }

    /**
     * Handle any request for server-sent event subscription
     * @return new emitter
     */
    @GetMapping(path = "/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe() {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        sseService.addEmitter(emitter);
        return emitter;
    }

}
