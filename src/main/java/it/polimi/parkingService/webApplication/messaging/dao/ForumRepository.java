package it.polimi.parkingService.webApplication.messaging.dao;

import it.polimi.parkingService.webApplication.messaging.models.Forum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForumRepository extends JpaRepository<Forum, String> {
}
