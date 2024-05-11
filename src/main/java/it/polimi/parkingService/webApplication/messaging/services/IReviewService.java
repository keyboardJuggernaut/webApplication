package it.polimi.parkingService.webApplication.messaging.services;

import it.polimi.parkingService.webApplication.messaging.models.Review;
import it.polimi.parkingService.webApplication.utils.BaseService;


public interface IReviewService extends BaseService<Review> {
    void completeReview(Review review, String username);
}
