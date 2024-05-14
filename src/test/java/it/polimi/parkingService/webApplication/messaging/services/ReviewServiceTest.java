package it.polimi.parkingService.webApplication.messaging.services;

import it.polimi.parkingService.webApplication.account.models.User;
import it.polimi.parkingService.webApplication.account.services.IUserService;
import it.polimi.parkingService.webApplication.messaging.dao.ReviewRepository;
import it.polimi.parkingService.webApplication.messaging.models.Review;
import it.polimi.parkingService.webApplication.parking.exceptions.ResourceNotFound;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ReviewServiceTest {


    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private IUserService userService;

    @InjectMocks
    private ReviewService reviewService;

    private Review review;
    private User user;

    @BeforeEach
    public void setUp() {
        review = new Review();
        review.setId(1L);
        user = new User();
        user.setUserName("testUsername");
    }

    @Test
    public void testCompleteReview_Success() {
        when(userService.findByUserName("testUsername")).thenReturn(user);

        reviewService.completeReview(review, "testUsername");

        assertEquals(user, review.getAuthor());
        assertNotNull(review.getTimestamp());
        verify(reviewRepository).save(review);
    }

    @Test
    public void testCompleteReview_UserNotFound() {
        when(userService.findByUserName("testUsername")).thenReturn(null);

        assertThrows(ResourceNotFound.class, () -> {
            reviewService.completeReview(review, "testUsername");
        });
    }

    @Test
    public void testFindAll() {
        reviewService.findAll();
        verify(reviewRepository).findAll();
    }

    @Test
    public void testFindById_Success() {
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));

        Review foundReview = reviewService.findById(1L);

        assertEquals(review, foundReview);
    }

    @Test
    public void testFindById_NotFound() {
        when(reviewRepository.findById(1L)).thenReturn(Optional.empty());

        Review foundReview = reviewService.findById(1L);

        assertNull(foundReview);
    }

    @Test
    public void testSave() {
        when(reviewRepository.save(review)).thenReturn(review);

        Review savedReview = reviewService.save(review);

        assertEquals(review, savedReview);
        verify(reviewRepository).save(review);
    }

    @Test
    public void testDeleteById() {
        reviewService.deleteById(1L);
        verify(reviewRepository).deleteById(1L);
    }
}
