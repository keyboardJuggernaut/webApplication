package it.polimi.parkingService.webApplication.messaging.services;

import it.polimi.parkingService.webApplication.messaging.dao.ReviewRepository;
import it.polimi.parkingService.webApplication.messaging.models.Forum;
import it.polimi.parkingService.webApplication.messaging.models.Review;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReviewService implements IReviewService{

    private ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
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
    public void save(Review review) {
        reviewRepository.save(review);
    }
}
