package it.polimi.parkingService.webApplication.analysis.controllers;

import it.polimi.parkingService.webApplication.analysis.DataAnalyzer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class AnalysisControllerTest {
    private MockMvc mockMvc;

    @Mock
    private DataAnalyzer dataAnalyzer;

    @InjectMocks
    private AnalysisController analysisController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(analysisController).build();
    }

    @Test
    public void testShowAnalysis() throws Exception {
        Map<String, Double> barChartData = new HashMap<>();
        barChartData.put("Monday", 1000.0);
        barChartData.put("Tuesday", 2000.0);

        Map<String, Integer> pieChartData = new HashMap<>();
        pieChartData.put("Monday", 10);
        pieChartData.put("Tuesday", 20);

        when(dataAnalyzer.getPeriodicIncome()).thenReturn(barChartData);
        when(dataAnalyzer.getPeriodicBooking()).thenReturn(pieChartData);

        mockMvc.perform(get("/analysis"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("barChartData", barChartData))
                .andExpect(model().attribute("pieChartData", pieChartData))
                .andExpect(view().name("analysis/analysis"));
    }
}
