package it.polimi.parkingService.webApplication.messaging.controllers;

import it.polimi.parkingService.webApplication.messaging.exceptions.AddingResponseForbidden;
import it.polimi.parkingService.webApplication.messaging.exceptions.InvalidThreadStatus;
import it.polimi.parkingService.webApplication.messaging.models.Reporting;
import it.polimi.parkingService.webApplication.messaging.models.Response;
import it.polimi.parkingService.webApplication.messaging.services.IReportingService;
import it.polimi.parkingService.webApplication.utils.AuthenticationFacadeUserUnauthenticated;
import it.polimi.parkingService.webApplication.utils.IAuthenticationFacade;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * The {@code ReportingController} handles any reporting related requests
 */
@Controller
@RequestMapping("/reportings")
public class ReportingController {


    private final IReportingService reportingService;


    private final IAuthenticationFacade authenticationFacade;

    /**
     * Constructs the controller
     * @param reportingService the service handling reporting business logic
     * @param authenticationFacade the service handling authentication info retrieval logic
     */
    public ReportingController(IReportingService reportingService, IAuthenticationFacade authenticationFacade ) {
        this.reportingService = reportingService;
        this.authenticationFacade = authenticationFacade;
    }

    /**
     * Handle any request for reporting list request
     * @param model the model for reporting
     * @return view reference
     */
    @GetMapping("")
    public String showReportings(Model model) {
        model.addAttribute("reportings", reportingService.findAll());
        return "/messaging/reportings/reportings";
    }

    /**
     * Handle any request for new reporting form request
     * @param model the model for reporting
     * @return the view reference
     */
    @GetMapping("/new")
    public String showReportingForm(Model model) {
        model.addAttribute("reporting", new Reporting());
        return "/messaging/reportings/form";
    }

    /**
     * Handle any request for reporting thread request
     * @param id the reporting id
     * @param model the model for reporting
     * @return the view reference
     */
    @GetMapping("/{id}")
    public String showThread(@PathVariable("id") Long id, Model model) {
        Reporting reporting = reportingService.findById(id);
        model.addAttribute("report", reporting);
        return "/messaging/reportings/reportings-thread";
    }

    /**
     * Handle any request for new reporting request
     * @param reporting the new reporting
     * @param redirectAttributes to perform redirecting
     * @return redirecting reference
     * @throws AuthenticationFacadeUserUnauthenticated if user is not authenticated
     */
    @PostMapping("")
    public String addReporting(@Valid @ModelAttribute("reporting") Reporting reporting, RedirectAttributes redirectAttributes) throws AuthenticationFacadeUserUnauthenticated {
        String username = authenticationFacade.getAuthenticatedUsername();
        Reporting fulfilledReporting =  reportingService.completeReporting(reporting, username);
        redirectAttributes.addAttribute("id", fulfilledReporting.getId());
        return "redirect:/reportings/{id}";
    }

    /**
     * Handle any request for set reporting status request
     * @param id the reporting id
     * @return redirecting reference
     * @throws InvalidThreadStatus if thread status is incompatible
     */
    @PostMapping("/{id}/close")
    public String closeThread(@PathVariable("id") Long id) throws InvalidThreadStatus {
        reportingService.updateThread(id);
        return "redirect:/reportings/" + id;
    }

    /**
     * Handle any request for new reporting response form request
     * @param id the reporting id
     * @param model the model for response
     * @return the view reference
     */
    @GetMapping("/{id}/responses/new")
    public String showResponseForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("reportId", id);
        model.addAttribute("response", new Response());
        return "/messaging/reportings/response-form";
    }

    /**
     * Handle any request for new reporting response request
     * @param id the reporting id
     * @param response the new response
     * @return redirecting reference
     * @throws AddingResponseForbidden if reporting thread has been closed
     * @throws AuthenticationFacadeUserUnauthenticated if user is not authenticated
     */
    @PostMapping("/{id}/responses")
    public String addResponse(@PathVariable("id") Long id,@ModelAttribute("response") Response response) throws AddingResponseForbidden, AuthenticationFacadeUserUnauthenticated {
        String username = authenticationFacade.getAuthenticatedUsername();
        reportingService.addResponseToReporting(id, response, username);
        return "redirect:/reportings";
    }
}
