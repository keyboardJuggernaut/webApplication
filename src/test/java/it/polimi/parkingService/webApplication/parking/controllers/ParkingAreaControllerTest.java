package it.polimi.parkingService.webApplication.parking.controllers;

import it.polimi.parkingService.webApplication.parking.models.Booking;
import it.polimi.parkingService.webApplication.parking.services.SseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class ParkingAreaControllerTest {
    private MockMvc mockMvc;

    @Mock
    private SseService sseService;

    @InjectMocks
    private ParkingAreaController parkingAreaController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(parkingAreaController).build();

    }

    @Test
    public void testSubscribe() throws Exception {
        mockMvc.perform(get("/parkingArea/sse"))
                .andExpect(status().isOk());
    }
}
