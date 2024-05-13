package it.polimi.parkingService.webApplication.account.controllers;

import it.polimi.parkingService.webApplication.account.models.User;
import it.polimi.parkingService.webApplication.account.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
public class RegistrationControllerTest {
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private RegistrationController registrationController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(registrationController).build();
    }

    @Test
    public void testShowRegistrationForm() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("paymentMethod"))
                .andExpect(view().name("account/registration-form"));
    }

    @Test
    public void testProcessRegistrationForm_ValidationErrors() throws Exception {
        mockMvc.perform(post("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("account/registration-confirmation"));
    }

    @Test
    public void testProcessRegistrationForm_UserAlreadyExists() throws Exception {
        User existingUser = new User();
        existingUser.setUserName("testUsername");

        when(userService.findByUserName("testUsername")).thenReturn(existingUser);

        mockMvc.perform(post("/register")
                        .param("userName", "testUsername"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("registrationError"))
                .andExpect(view().name("account/registration-form"));

        verify(userService, times(1)).findByUserName("testUsername");
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void testProcessRegistrationForm_Success() throws Exception {
        User newUser = new User();
        newUser.setUserName("testUsername");

        mockMvc.perform(post("/register")
                        .param("userName", "testUsername"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeDoesNotExist("registrationError"))
                .andExpect(view().name("account/registration-confirmation"));

        verify(userService, times(1)).findByUserName("testUsername");

    }
}
