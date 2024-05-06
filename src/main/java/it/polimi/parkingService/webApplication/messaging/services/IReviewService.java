package it.polimi.parkingService.webApplication.messaging.services;

import it.polimi.parkingService.webApplication.messaging.models.Forum;
import it.polimi.parkingService.webApplication.messaging.models.Review;

public interface IReviewService {

    public Review findById(long id);
    public void save(Review review);
}
