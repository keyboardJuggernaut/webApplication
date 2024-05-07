package it.polimi.parkingService.webApplication.messaging.dao;

import it.polimi.parkingService.webApplication.messaging.models.Reporting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportingRepository extends JpaRepository<Reporting, Long> {
}
