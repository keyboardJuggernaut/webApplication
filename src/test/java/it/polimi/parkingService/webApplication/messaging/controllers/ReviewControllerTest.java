package it.polimi.parkingService.webApplication.messaging.controllers;

import it.polimi.parkingService.webApplication.common.IAuthenticationFacade;
import it.polimi.parkingService.webApplication.messaging.models.Review;
import it.polimi.parkingService.webApplication.messaging.services.IReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class ReviewControllerTest {
    private MockMvc mockMvc;

    @Mock
    private IReviewService reviewService;

    @Mock
    private IAuthenticationFacade authenticationFacade;

    @InjectMocks
    private ReviewController reviewController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(reviewController).build();
    }

    @Test
    public void testShowReviews() throws Exception {
        List<Review> reviews = Collections.emptyList();
        when(reviewService.findAll()).thenReturn(reviews);

        mockMvc.perform(get("/reviews"))
                .andExpect(status().isOk())
                .andExpect(view().name("/messaging/reviews/reviews-main-page"))
                .andExpect(model().attribute("reviews", reviews));

        verify(reviewService).findAll();
    }
    @Test
    public void testShowReviewForm() throws Exception {
        mockMvc.perform(get("/reviews/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("/messaging/reviews/review-form"))
                .andExpect(model().attributeExists("review"));
    }

    @Test
    public void testAddReview() throws Exception {
        Review review = new Review();
        String username = "testUser";

        when(authenticationFacade.getAuthenticatedUsername()).thenReturn(username);

        mockMvc.perform(post("/reviews")
                        .flashAttr("review", review)
                )
                .andExpect(redirectedUrl("/reviews"));

        verify(authenticationFacade).getAuthenticatedUsername();
        verify(reviewService).completeReview(eq(review), eq(username));
    }
}
