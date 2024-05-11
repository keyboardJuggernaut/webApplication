package it.polimi.parkingService.webApplication.account.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * The {@code LoginController} handles any authentication related requests
 */
@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLoginPage() {
        return "account/login";
    }

    @GetMapping("/access-denied")
    public String showAccessDenied() {
        return "common/access-denied";
    }
}
