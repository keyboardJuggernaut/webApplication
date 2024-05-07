package it.polimi.parkingService.webApplication.messaging.controllers;

import it.polimi.parkingService.webApplication.account.service.IUserService;
import it.polimi.parkingService.webApplication.messaging.models.Forum;
import it.polimi.parkingService.webApplication.messaging.models.Message;
import it.polimi.parkingService.webApplication.messaging.models.Review;
import it.polimi.parkingService.webApplication.messaging.services.IForumService;
import it.polimi.parkingService.webApplication.messaging.services.IReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
@RequestMapping("/forum")
public class ForumController {

    @Value("${review.forum.name}")
    private String REVIEWS_FORUM_NAME;

    @Value("${reporting.forum.name}")
    private String REPORTING_FORUM_NAME;

    private IForumService forumService;
    private IUserService userService;

    private IReviewService reviewService;



    @Autowired
    public ForumController(IForumService forumService, IUserService userService, IReviewService reviewService) {
        this.forumService = forumService;
        this.userService = userService;
        this.reviewService = reviewService;
    }

    @GetMapping("/reviews")
    public String showReviews(Model model) {
        Forum reviewForum = forumService.findById(REVIEWS_FORUM_NAME);
        model.addAttribute("reviews", reviewForum.getMessages());
        return "forum/reviews-main-page";
    }

    @GetMapping("/reviews/add")
    public String showReviewForm(Model model) {
        model.addAttribute("review", new Review());
        return "forum/review-form";
    }

    @PostMapping("/reviews")
    public String addReview(@Valid @ModelAttribute("review") Review review) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalUsername = authentication.getName();
        review.setAuthor(userService.findByUserName(currentPrincipalUsername));
        Forum reviewForum = forumService.findById(REVIEWS_FORUM_NAME);
        review.setForum(reviewForum);
        review.setTimestamp(LocalDateTime.now());
        reviewService.save(review);
        return "redirect:/forum/reviews";
    }

}
