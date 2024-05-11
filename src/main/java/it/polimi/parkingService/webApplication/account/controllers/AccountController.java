package it.polimi.parkingService.webApplication.account.controllers;

import it.polimi.parkingService.webApplication.account.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * The {@code AccountController} handles any account related requests
 */
@Controller
@RequestMapping("/accounts")
public class AccountController {

    /**
     * Handle any request for new user form request
     * @param model the model for user
     * @return view reference
     */
    @GetMapping("/showForm")
    public String showRegistrationForm(Model model) {
        model.addAttribute("account", new User());
        return "account/account-registration-form";
    }

}
