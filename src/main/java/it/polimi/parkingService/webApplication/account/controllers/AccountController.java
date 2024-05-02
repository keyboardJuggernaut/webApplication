package it.polimi.parkingService.webApplication.account.controllers;

import it.polimi.parkingService.webApplication.account.models.User;
import it.polimi.parkingService.webApplication.account.models.Role;
import it.polimi.parkingService.webApplication.account.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/accounts")
public class AccountController {

    private UserService accountService;

    @Autowired
    public AccountController(UserService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/showForm")
    public String showRegistrationForm(Model model) {
        model.addAttribute("account", new User());
        return "account/account-registration-form";
    }

//    @PostMapping("/save")
//    public String save(@ModelAttribute("account") User account) {
//        account.addRole(new Role());
//        accountService.save(account);
//        return "account/registration-confirmation";
//    }
}
