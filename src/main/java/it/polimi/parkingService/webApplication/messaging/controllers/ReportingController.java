package it.polimi.parkingService.webApplication.messaging.controllers;

import it.polimi.parkingService.webApplication.account.service.IUserService;
import it.polimi.parkingService.webApplication.messaging.enums.ReportingSeverity;
import it.polimi.parkingService.webApplication.messaging.models.Forum;
import it.polimi.parkingService.webApplication.messaging.models.Reporting;
import it.polimi.parkingService.webApplication.messaging.services.IReportingService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@Controller
public class ReportingController {

    private IReportingService reportingService;

    private IUserService userService;

    public ReportingController(IReportingService reportingService, IUserService userService) {
        this.reportingService = reportingService;
        this.userService = userService;
    }

    @GetMapping("/reportings")
    public String showReportings(Model model) {
        model.addAttribute("reportings", reportingService.findAll());
        return "/reportings/reportings";
    }

    @GetMapping("/reportings/add")
    public String showReportingForm(Model model) {
        model.addAttribute("reporting", new Reporting());

        return "/reportings/form";
    }

    @PostMapping("/reportings")
    public String addReporting(@Valid @ModelAttribute("reporting") Reporting reporting) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalUsername = authentication.getName();
        reporting.setAuthor(userService.findByUserName(currentPrincipalUsername));
        reporting.setTimestamp(LocalDateTime.now());
        reportingService.save(reporting);
        return "redirect:/reportings";
    }
}
