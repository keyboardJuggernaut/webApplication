package it.polimi.parkingService.webApplication.parking.controllers;

import it.polimi.parkingService.webApplication.parking.exceptions.ResourceNotFound;
import it.polimi.parkingService.webApplication.parking.exceptions.SearchStrategyUndefined;
import it.polimi.parkingService.webApplication.parking.models.Booking;
import it.polimi.parkingService.webApplication.parking.services.IBookingService;
import it.polimi.parkingService.webApplication.parking.services.IParkingSpotService;
import it.polimi.parkingService.webApplication.common.AuthenticationFacadeUserUnauthenticated;
import it.polimi.parkingService.webApplication.common.IAuthenticationFacade;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The {@code BookingController} handles any booking related requests
 */
@Controller
@RequestMapping("/bookings")
public class BookingController {

    private final IParkingSpotService parkingSpotService;
    private final IBookingService bookingService;

    private final IAuthenticationFacade authenticationFacade;

    /**
     * Constructs the controller
     * @param parkingSpotService the service handling parking spot business logic
     * @param bookingService the service handling booking business logic
     * @param authenticationFacade the service handling authentication info retrieval logic
     */
    @Autowired
    public BookingController(IParkingSpotService parkingSpotService, IBookingService bookingService, IAuthenticationFacade authenticationFacade) {
        this.parkingSpotService = parkingSpotService;
        this.bookingService = bookingService;
        this.authenticationFacade = authenticationFacade;
    }

    /**
     * Handle any request for new booking form request
     * @param model the model for booking
     * @return view reference
     */
    @GetMapping("/new")
    public String showBookingForm(Model model) {
        model.addAttribute("booking", new Booking());
        return "parking/booking/booking-form";
    }

    /**
     * Handle any request for booking list request
     * @param model the model for booking
     * @return view reference
     */
    @GetMapping("/admin")
    public String showBookings(Model model) {
        List<Booking> bookings = bookingService.findAll();
        model.addAttribute("bookings", bookings);
        return "parking/booking/bookings";
    }

    /**
     * Handle any request for booking cancellation request
     * @param id the booking id
     * @return redirecting reference
     */
    @PostMapping("/{id}")
    public String cancelBooking(@PathVariable("id") Long id) throws ResourceNotFound  {
        bookingService.cancelBooking(id);
        return "redirect:/bookings/admin";
    }

    /**
     * Handle any request for new booking request
     * @param booking the booking instance
     * @param theBindingResult the validation results
     * @param model the model for booking
     * @return view reference
     */
    @PostMapping("")
    public String addBooking(@Valid @ModelAttribute("booking") Booking booking, BindingResult theBindingResult, Model model) throws AuthenticationFacadeUserUnauthenticated, SearchStrategyUndefined {

        if (theBindingResult.hasErrors()){
            return "parking/booking/booking-form";
        }
        String username = authenticationFacade.getAuthenticatedUsername();
        Booking confirmedBooking = parkingSpotService.reserveParkingSpot(username, booking);
        model.addAttribute("confirmedBooking", confirmedBooking);
        return "parking/booking/booking-confirmation";
    }
}
