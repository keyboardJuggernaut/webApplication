package it.polimi.parkingService.webApplication.messaging.controllers;

import it.polimi.parkingService.webApplication.messaging.models.Forum;
import it.polimi.parkingService.webApplication.messaging.services.IForumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/forum")
public class ForumController {

    @Value("${review.forum.name}")
    private String REVIEWS_FORUM_NAME;

    @Value("${reporting.forum.name}")
    private String REPORTING_FORUM_NAME;

    IForumService forumService;

    @Autowired
    public ForumController(IForumService forumService) {
        this.forumService = forumService;
    }

    @GetMapping("/reviews")
    public String showReviews(Model model) {
        Forum reviewForum = forumService.findById(REVIEWS_FORUM_NAME);
        model.addAttribute("reviews", reviewForum.getMessages());
        return "forum/reviews-main-page";
    }
}
