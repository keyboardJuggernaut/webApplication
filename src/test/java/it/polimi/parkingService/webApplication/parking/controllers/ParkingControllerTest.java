package it.polimi.parkingService.webApplication.parking.controllers;

import it.polimi.parkingService.webApplication.common.IAuthenticationFacade;
import it.polimi.parkingService.webApplication.parking.models.Parking;
import it.polimi.parkingService.webApplication.parking.services.IParkingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class ParkingControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IParkingService parkingService;

    @Mock
    private IAuthenticationFacade authenticationFacade;
    
    @InjectMocks
    private ParkingController parkingController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(parkingController).build();
    }

    @Test
    public void testShowCheckIn() throws Exception {
        when(authenticationFacade.getAuthenticatedUsername()).thenReturn("testUsername");
        when(parkingService.getCheckInQRCode("testUsername")).thenReturn("testQRCode");

        mockMvc.perform(get("/parkings/checkin"))
                .andExpect(status().isOk())
                .andExpect(view().name("parking/checkin"))
                .andExpect(model().attributeExists("qrcode"))
                .andExpect(model().attribute("qrcode", "testQRCode"));
    }

    @Test
    public void testShowCheckOut() throws Exception {
        when(authenticationFacade.getAuthenticatedUsername()).thenReturn("testUsername");
        when(parkingService.getCheckOutQRCode("testUsername")).thenReturn("testQRCode");

        mockMvc.perform(get("/parkings/checkout"))
                .andExpect(status().isOk())
                .andExpect(view().name("parking/checkout"))
                .andExpect(model().attributeExists("qrcode"))
                .andExpect(model().attribute("qrcode", "testQRCode"));
    }

    @Test
    public void testSetEstimatedTime() throws Exception {
        Parking parking = new Parking();
        parking.setId(1L);
        parking.setEstimatedTime(LocalTime.now());
        mockMvc.perform(post("/parkings").flashAttr("parking", parking))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/spots"));

        verify(parkingService).update(anyLong(), any());
    }
}
