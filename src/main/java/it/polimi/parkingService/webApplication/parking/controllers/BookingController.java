package it.polimi.parkingService.webApplication.parking.controllers;

import it.polimi.parkingService.webApplication.parking.exceptions.ResourceNotFound;
import it.polimi.parkingService.webApplication.parking.models.Booking;
import it.polimi.parkingService.webApplication.parking.services.IBookingService;
import it.polimi.parkingService.webApplication.parking.services.IParkingAreaService;
import it.polimi.parkingService.webApplication.payment.models.PaymentSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/parkingArea")
public class BookingController {

    private IParkingAreaService parkingAreaService;
    private IBookingService bookingService;

    private PaymentSystem paymentSystem;

    @Autowired
    private BookingController(IParkingAreaService parkingAreaService, IBookingService bookingService, PaymentSystem paymentSystem){
        this.parkingAreaService = parkingAreaService;
        this.bookingService = bookingService;
        this.paymentSystem = paymentSystem;
    }


    @GetMapping("/bookings")
    public String showBookingForm(Model model) {
        model.addAttribute("booking", new Booking());
        return "parkingArea/booking/booking-form";
    }

    @GetMapping("/bookings/admin")
    public String showBookingMonitoring(Model model) {
        List<Booking> bookings = bookingService.findAll();
        model.addAttribute("bookings", bookings);
        return "parkingArea/booking/bookings";
    }

    @PostMapping("/bookings/{id}")
    public String cancelBooking(@PathVariable("id") Long id, Model model) {
        Optional<Booking> result = bookingService.findById(id);
        if(result.isEmpty()) {
            throw new ResourceNotFound("Booking with id " + id + "is not present");
        }
        Booking booking = result.get();
        booking.refund(paymentSystem, true);
        bookingService.deleteById(id);
        return "redirect:/parkingArea/bookings/admin";
    }

    @PostMapping("/reserving")
    public String processBookingForm(@ModelAttribute("booking") Booking booking, BindingResult theBindingResult, Model model) {

        if (theBindingResult.hasErrors()){
            return "parkingArea/booking/booking-form";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalUsername = authentication.getName();

        Booking confirmedBooking = parkingAreaService.reserveParkingSpot(currentPrincipalUsername, booking);
        model.addAttribute("confirmedBooking", confirmedBooking);

        return "parkingArea/booking/booking-confirmation";
    }
}
