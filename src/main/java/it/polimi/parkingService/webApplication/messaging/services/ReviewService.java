package it.polimi.parkingService.webApplication.messaging.services;

import it.polimi.parkingService.webApplication.account.models.User;
import it.polimi.parkingService.webApplication.account.service.IUserService;
import it.polimi.parkingService.webApplication.messaging.dao.ReviewRepository;
import it.polimi.parkingService.webApplication.messaging.models.Review;
import it.polimi.parkingService.webApplication.parking.exceptions.ResourceNotFound;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * The {@code ReviewService} handles any review related business logic
 */
@Service
public class ReviewService implements IReviewService {

    private final ReviewRepository reviewRepository;
    private final IUserService userService;

    /**
     * Constructs the service
     * @param reviewRepository the repository handling review persistence logic
     * @param userService the service handling user business logic
     */
    public ReviewService(ReviewRepository reviewRepository, IUserService userService) {
        this.reviewRepository = reviewRepository;
        this.userService = userService;
    }

    /**
     * Completes the new review
     * @param review the new review
     * @param username author's username
     */
    @Override
    public void completeReview(Review review, String username) {
        User user = userService.findByUserName(username);
        if(user == null) {
            throw new ResourceNotFound("User not found");
        }
        review.setAuthor(user);
        review.setTimestamp(LocalDateTime.now());
        save(review);
    }

    @Override
    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    @Override
    public Review findById(long id) {
        Optional<Review> result = reviewRepository.findById(id);

        Review review = null;

        if (result.isPresent()) {
            review = result.get();
            return review;
        }
        return review;
    }

    @Override
    public Review save(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public void deleteById(long id) {
        reviewRepository.deleteById(id);
    }
}
