package it.polimi.parkingService.webApplication.messaging.controllers;

import it.polimi.parkingService.webApplication.common.AuthenticationFacadeUserUnauthenticated;
import it.polimi.parkingService.webApplication.common.IAuthenticationFacade;
import it.polimi.parkingService.webApplication.messaging.models.Reporting;
import it.polimi.parkingService.webApplication.messaging.models.Response;
import it.polimi.parkingService.webApplication.messaging.services.IReportingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

 @SpringBootTest
public class ReportingControllerTest {
    private MockMvc mockMvc;

    @Mock
    private IReportingService reportingService;

    @Mock
    private IAuthenticationFacade authenticationFacade;

    @InjectMocks
    private ReportingController reportingController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(reportingController).build();
    }

    @Test
    public void testShowReportings() throws Exception {
        when(reportingService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/reportings"))
                .andExpect(status().isOk())
                .andExpect(view().name("/messaging/reportings/reportings"))
                .andExpect(model().attributeExists("reportings"));

        verify(reportingService).findAll();
    }

    @Test
    public void testShowReportingForm() throws Exception {
        mockMvc.perform(get("/reportings/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("/messaging/reportings/form"))
                .andExpect(model().attributeExists("reporting"));
    }

    @Test
    public void testShowThread() throws Exception {
        long id = 123L;
        Reporting reporting = new Reporting();
        reporting.setId(id);
        when(reportingService.findById(id)).thenReturn(reporting);

        mockMvc.perform(get("/reportings/{id}", id))
                .andExpect(status().isOk())
                .andExpect(view().name("/messaging/reportings/reportings-thread"))
                .andExpect(model().attribute("report", reporting));

        verify(reportingService).findById(id);
    }


     @Test
     public void testAddReporting_Success() throws Exception {
         Reporting reporting = new Reporting();
         reporting.setId(123L);
         when(authenticationFacade.getAuthenticatedUsername()).thenReturn("testUser");
         when(reportingService.completeReporting(any(Reporting.class), eq("testUser"))).thenReturn(reporting);

         mockMvc.perform(post("/reportings")
                         .flashAttr("reporting", reporting))
                 .andExpect(status().is3xxRedirection())
                 .andExpect(redirectedUrl("/reportings/123"));

         verify(reportingService).completeReporting(any(Reporting.class), eq("testUser"));
     }


    @Test
    public void testCloseThread() throws Exception {
        long id = 123L;

        mockMvc.perform(post("/reportings/{id}/close", id))
                .andExpect(redirectedUrl("/reportings/" + id));

        verify(reportingService).updateThread(id);
    }

    @Test
    public void testShowResponseForm() throws Exception {
        Long id = 123L;

        mockMvc.perform(get("/reportings/{id}/responses/new", id))
                .andExpect(status().isOk())
                .andExpect(view().name("/messaging/reportings/response-form"))
                .andExpect(model().attribute("reportId", id))
                .andExpect(model().attributeExists("response"));
    }

    @Test
    public void testAddResponse() throws Exception {
        Long id = 123L;
        Response response = new Response();
        String username = "testUser";

        when(authenticationFacade.getAuthenticatedUsername()).thenReturn(username);

        mockMvc.perform(post("/reportings/{id}/responses", id)
                        .flashAttr("response", response)
                )
                .andExpect(redirectedUrl("/reportings"));

        verify(authenticationFacade).getAuthenticatedUsername();
        verify(reportingService).addResponseToReporting(eq(id), eq(response), eq(username));
    }

}
