package it.polimi.parkingService.webApplication.messaging.services;

import it.polimi.parkingService.webApplication.messaging.models.Review;

import java.util.List;

public interface IReviewService {

    List<Review> findAll();

    public Review findById(long id);
    public void save(Review review);
}
