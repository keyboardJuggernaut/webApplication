package it.polimi.parkingService.webApplication.parking.controllers;

import it.polimi.parkingService.webApplication.account.controllers.RegistrationController;
import it.polimi.parkingService.webApplication.common.IAuthenticationFacade;
import it.polimi.parkingService.webApplication.parking.models.Booking;
import it.polimi.parkingService.webApplication.parking.services.IBookingService;
import it.polimi.parkingService.webApplication.parking.services.IParkingSpotService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class BookingControllerTest {
    private MockMvc mockMvc;

    @Mock
    private IParkingSpotService parkingSpotService;

    @Mock
    private IBookingService bookingService;

    @Mock
    private IAuthenticationFacade authenticationFacade;

    private Booking booking;
    private List<Booking> bookings;

    @InjectMocks
    private BookingController bookingController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(bookingController).build();
        booking = new Booking();
        bookings = new ArrayList<>();
        bookings.add(booking);
    }

    @Test
    public void testShowBookingForm() throws Exception {
        mockMvc.perform(get("/bookings/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("parking/booking/booking-form"))
                .andExpect(model().attributeExists("booking"));
    }

    @Test
    public void testShowBookings() throws Exception {
        when(bookingService.findAll()).thenReturn(bookings);

        mockMvc.perform(get("/bookings/admin"))
                .andExpect(status().isOk())
                .andExpect(view().name("parking/booking/bookings"))
                .andExpect(model().attributeExists("bookings"));
    }

    @Test
    public void testCancelBooking() throws Exception {
        mockMvc.perform(post("/bookings/{id}", 1L))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bookings/admin"));

        verify(bookingService).cancelBooking(1L);
    }

    @Test
    public void testAddBooking_Success() throws Exception {
        when(authenticationFacade.getAuthenticatedUsername()).thenReturn("testUser");
        when(parkingSpotService.reserveParkingSpot(anyString(), any(Booking.class))).thenReturn(booking);

        mockMvc.perform(post("/bookings")
                        .flashAttr("booking", booking))
                .andExpect(status().isOk())
                .andExpect(view().name("parking/booking/booking-confirmation"))
                .andExpect(model().attributeExists("confirmedBooking"));
    }


}
