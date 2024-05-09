package it.polimi.parkingService.webApplication.messaging.controllers;

import it.polimi.parkingService.webApplication.account.service.IUserService;

import it.polimi.parkingService.webApplication.messaging.models.Review;
import it.polimi.parkingService.webApplication.messaging.services.IReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/reviews")
public class ReviewController {


    private IUserService userService;

    private IReviewService reviewService;



    @Autowired
    public ReviewController(IUserService userService, IReviewService reviewService) {
        this.userService = userService;
        this.reviewService = reviewService;
    }

    @GetMapping("")
    public String showReviews(Model model) {
        List<Review> reviews = reviewService.findAll();
        model.addAttribute("reviews", reviews);
        return "reviews/reviews-main-page";
    }

    @GetMapping("/add")
    public String showReviewForm(Model model) {
        model.addAttribute("review", new Review());
        return "reviews/review-form";
    }

    @PostMapping("")
    public String addReview(@Valid @ModelAttribute("review") Review review) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalUsername = authentication.getName();
        review.setAuthor(userService.findByUserName(currentPrincipalUsername));
        review.setTimestamp(LocalDateTime.now());
        reviewService.save(review);
        return "redirect:/reviews";
    }

}
