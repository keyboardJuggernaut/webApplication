package it.polimi.parkingService.webApplication.parking.controllers;

import it.polimi.parkingService.webApplication.parking.models.Parking;
import it.polimi.parkingService.webApplication.parking.models.ParkingSpot;
import it.polimi.parkingService.webApplication.parking.services.IParkingSpotService;
import it.polimi.parkingService.webApplication.payment.models.PaymentReceipt;
import it.polimi.parkingService.webApplication.utils.QRCodeGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class ParkingSpotControllerTest {
    private MockMvc mockMvc;

    @Mock
    private QRCodeGenerator qrCodeGenerator;

    @Mock
    private IParkingSpotService parkingSpotService;

    @InjectMocks
    private ParkingSpotController parkingSpotController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(parkingSpotController).build();
    }

    @Test
    public void testShowSpots() throws Exception {
        Map<ParkingSpot, Parking> parkingArea = new HashMap<>();
        when(parkingSpotService.getSpotsWithParkings()).thenReturn(parkingArea);

        mockMvc.perform(get("/spots"))
                .andExpect(status().isOk())
                .andExpect(view().name("parking/map"))
                .andExpect(model().attributeExists("parkingArea"))
                .andExpect(model().attribute("parkingArea", parkingArea));
    }

//    @Test
//    public void testDoCheckIn() throws Exception {
//        Parking parking = new Parking();
//        when(parkingSpotService.startParking(any())).thenReturn(parking);
//        try (MockedStatic<QRCodeGenerator> utilities = Mockito.mockStatic(QRCodeGenerator.class)) {
//
//            utilities.when(() -> QRCodeGenerator.decodeQRCodeEncodedImage("testQRCode"))
//                    .thenReturn("encodedContent");
//        }
//
//
//        mockMvc.perform(post("/spots/checkin").param("qrcode", "testQRCode"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("parking/parking-confirmation"))
//                .andExpect(model().attributeExists("parking"))
//                .andExpect(model().attribute("parking", parking));
//    }
//
//    @Test
//    public void testDoCheckout() throws Exception {
//        PaymentReceipt receipt = new PaymentReceipt();
//        when(parkingSpotService.doCheckout(any())).thenReturn(receipt);
//
//        mockMvc.perform(post("/spots/checkout").param("qrcode", "testQRCode"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("parking/checkout-confirmation"))
//                .andExpect(model().attributeExists("receipt"))
//                .andExpect(model().attribute("receipt", receipt));
//    }

    @Test
    public void testToggleAvailability() throws Exception {
        mockMvc.perform(post("/spots/{id}/toggleAvailability", 1L))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/spots"));

        verify(parkingSpotService).toggleSpotAvailability(1L);
    }
}
