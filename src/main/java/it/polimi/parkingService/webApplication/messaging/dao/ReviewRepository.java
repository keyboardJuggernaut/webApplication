package it.polimi.parkingService.webApplication.messaging.dao;

import it.polimi.parkingService.webApplication.messaging.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
