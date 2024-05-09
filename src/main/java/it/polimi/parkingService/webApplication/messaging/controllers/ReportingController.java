package it.polimi.parkingService.webApplication.messaging.controllers;

import it.polimi.parkingService.webApplication.account.service.IUserService;
import it.polimi.parkingService.webApplication.messaging.enums.ReportingSeverity;
import it.polimi.parkingService.webApplication.messaging.exceptions.AddingResponseForbidden;
import it.polimi.parkingService.webApplication.messaging.models.Reporting;
import it.polimi.parkingService.webApplication.messaging.models.Response;
import it.polimi.parkingService.webApplication.messaging.services.IReportingService;
import it.polimi.parkingService.webApplication.messaging.services.IResponseService;
import it.polimi.parkingService.webApplication.parking.enums.ParkingSpotStatus;
import it.polimi.parkingService.webApplication.parking.models.ParkingSpot;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;

@Controller
public class ReportingController {

    // TODO: aggiunta notifica con sse a gestori

    private IReportingService reportingService;

    private IUserService userService;

    private IResponseService responseService;

    public ReportingController(IReportingService reportingService, IUserService userService,IResponseService responseService ) {
        this.reportingService = reportingService;
        this.userService = userService;
        this.responseService = responseService;
    }

    @GetMapping("/reportings")
    public String showReportings(Model model) {
        model.addAttribute("reportings", reportingService.findAll());
        return "/reportings/reportings";
    }
    @GetMapping("/reportings/{id}")
    public String showReportings(@PathVariable("id") Long id, Model model) {
        Reporting reporting = reportingService.findById(id);
        model.addAttribute("report", reporting);
        return "/reportings/reportings-thread";
    }

    @PostMapping("/reportings/{id}/close")
    public String closeThread(@PathVariable("id") Long id) {
        Reporting report = reportingService.findById(id);
        if(report.isOpen()) {
            report.setOpen(false);
        } else {
            throw new RuntimeException("Thread already closed");
        }
        reportingService.save(report);
        return "redirect:/reportings/" + id;
    }


    @GetMapping("/reportings/add")
    public String showReportingForm(Model model) {
        model.addAttribute("reporting", new Reporting());

        return "/reportings/form";
    }

    @PostMapping("/reportings")
    public String addReporting(@Valid @ModelAttribute("reporting") Reporting reporting, RedirectAttributes redirectAttributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalUsername = authentication.getName();
        reporting.setAuthor(userService.findByUserName(currentPrincipalUsername));
        reporting.setTimestamp(LocalDateTime.now());
        reportingService.save(reporting);
        redirectAttributes.addAttribute("id", reporting.getId());
        return "redirect:/reportings/{id}";
    }

    @GetMapping("/reportings/{id}/responses/add")
    public String showResponseForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("reportId", id);
        model.addAttribute("response", new Response());
        return "/reportings/response-form";
    }

    @PostMapping("/reportings/{id}/responses")
    public String addResponse(@PathVariable("id") Long id,@ModelAttribute("response") Response response) throws AddingResponseForbidden {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalUsername = authentication.getName();
        response.setAuthor(userService.findByUserName(currentPrincipalUsername));
        response.setTimestamp(LocalDateTime.now());

        Reporting reporting = reportingService.findById(id);
        reporting.addResponse(response);

        reportingService.save(reporting);

        return "redirect:/reportings";
    }
}
