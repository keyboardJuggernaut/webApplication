package it.polimi.parkingService.webApplication.messaging.dao;

import it.polimi.parkingService.webApplication.messaging.models.Response;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResponseRepository extends JpaRepository<Response, Long> {
}
