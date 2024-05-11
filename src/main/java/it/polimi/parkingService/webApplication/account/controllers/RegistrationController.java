package it.polimi.parkingService.webApplication.account.controllers;

import it.polimi.parkingService.webApplication.account.models.User;
import it.polimi.parkingService.webApplication.account.service.UserService;
import it.polimi.parkingService.webApplication.payment.models.PaymentMethod;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

/**
 * The {@code RegistrationController} handles any registration related requests
 */
@Controller
@RequestMapping("/register")
public class RegistrationController {
    private final UserService userService;

    /**
     * Constructs the controller
     * @param userService the service handling user business logic
     */
    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {

        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("paymentMethod", new PaymentMethod());
        return "account/registration-form";
    }

    @PostMapping("")
    public String processRegistrationForm(
            @Valid @ModelAttribute("user") User user,
            @Valid @ModelAttribute("paymentMethod") PaymentMethod paymentMethod,
            BindingResult theBindingResult,
            HttpSession session, Model theModel) {

        String userName = user.getUserName();

        // form validation
        if (theBindingResult.hasErrors()){
            return "account/registration-form";
        }

        // check  if user already exists
        User existing = userService.findByUserName(userName);
        if (existing != null){
            theModel.addAttribute("user", new User());
            theModel.addAttribute("registrationError", "User name already exists.");

            return "account/registration-form";
        }

        user.setPaymentMethod(paymentMethod);
        userService.save(user);


        // place user in the web http session for later use
        session.setAttribute("user", user);

        return "account/registration-confirmation";
    }
}
