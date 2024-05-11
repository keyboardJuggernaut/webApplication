package it.polimi.parkingService.webApplication.messaging.controllers;


import it.polimi.parkingService.webApplication.messaging.models.Review;
import it.polimi.parkingService.webApplication.messaging.services.IReviewService;
import it.polimi.parkingService.webApplication.common.AuthenticationFacadeUserUnauthenticated;
import it.polimi.parkingService.webApplication.common.IAuthenticationFacade;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * The {@code ReviewController} handles any review related requests
 */
@Controller
@RequestMapping("/reviews")
public class ReviewController {



    private final IReviewService reviewService;
    private final IAuthenticationFacade authenticationFacade;

    /**
     * Constructs the controller
     * @param reviewService the service handling review business logic
     * @param authenticationFacade the service handling authentication info retrieval logic
     */
    public ReviewController(IReviewService reviewService, IAuthenticationFacade authenticationFacade) {
        this.reviewService = reviewService;
        this.authenticationFacade = authenticationFacade;
    }

    /**
     * Handle any request for review list request
     * @param model the model for reporting
     * @return view reference
     */
    @GetMapping("")
    public String showReviews(Model model) {
        List<Review> reviews = reviewService.findAll();
        model.addAttribute("reviews", reviews);
        return "/messaging/reviews/reviews-main-page";
    }

    /**
     * Handle any request for new review form request
     * @param model the model for reporting
     * @return the view reference
     */
    @GetMapping("/new")
    public String showReviewForm(Model model) {
        model.addAttribute("review", new Review());
        return "/messaging/reviews/review-form";
    }

    /**
     * Handle any request for new review request
     * @param review the new review
     * @return redirecting reference
     * @throws AuthenticationFacadeUserUnauthenticated if user is not authenticated
     */
    @PostMapping("")
    public String addReview(@Valid @ModelAttribute("review") Review review) throws AuthenticationFacadeUserUnauthenticated {
        String username = authenticationFacade.getAuthenticatedUsername();
        reviewService.completeReview(review, username);
        return "redirect:/reviews";
    }

}
