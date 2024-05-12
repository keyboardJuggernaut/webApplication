package it.polimi.parkingService.webApplication.messaging.services;

import it.polimi.parkingService.webApplication.account.models.User;
import it.polimi.parkingService.webApplication.account.services.IUserService;
import it.polimi.parkingService.webApplication.messaging.dao.ReportingRepository;
import it.polimi.parkingService.webApplication.messaging.exceptions.AddingResponseForbidden;
import it.polimi.parkingService.webApplication.messaging.exceptions.InvalidThreadStatus;
import it.polimi.parkingService.webApplication.messaging.models.Reporting;
import it.polimi.parkingService.webApplication.messaging.models.Response;
import it.polimi.parkingService.webApplication.parking.exceptions.ResourceNotFound;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * The {@code ReportingService} handles any reporting related business logic
 */
@Service
public class ReportingService implements IReportingService{
    private final ReportingRepository reportingRepository;
    private final IUserService userService;

    /**
     * Constructs the services
     * @param reportingRepository the repository handling reporting persistence logic
     * @param userService the services handling user business logic
     */
    public ReportingService(ReportingRepository reportingRepository, IUserService userService) {
        this.reportingRepository = reportingRepository;
        this.userService = userService;
    }

    /**
     * Completes new reporting fields
     * @param reporting the new reporting
     * @param username the author's username
     * @return the fulfilled reporting
     */
    @Override
    public Reporting completeReporting(Reporting reporting, String username) {
        User user = userService.findByUserName(username);
        if(user == null) {
            throw new ResourceNotFound("User not found");
        }
        reporting.setAuthor(user);
        reporting.setTimestamp(LocalDateTime.now());
        return save(reporting);
    }

    /**
     * Updates thread status closing the reporting
     * @param id the reporting id
     * @throws InvalidThreadStatus if reporting status is invalid
     */
    @Override
    public void updateThread(long id) throws InvalidThreadStatus {
        Reporting report = findById(id);
        if(report.isOpen()) {
            report.setOpen(false);
        } else {
            throw new InvalidThreadStatus("Thread already closed");
        }
        save(report);
    }

    /**
     * Adds response to existing reporting
     * @param reportingId the reporting id
     * @param response the linked response
     * @param username the response author
     * @throws AddingResponseForbidden if reporting has been previously closed
     */
    @Override
    public void addResponseToReporting(long reportingId, Response response, String username) throws AddingResponseForbidden {
        User user = userService.findByUserName(username);
        if(user == null) {
            throw new ResourceNotFound("User not found");
        }
        response.setAuthor(user);
        response.setTimestamp(LocalDateTime.now());

        Reporting reporting = findById(reportingId);
        reporting.addResponse(response);

        save(reporting);

    }

    @Override
    public List<Reporting> findAll() {
        return reportingRepository.findAll();
    }

    @Override
    public Reporting findById(long id) {

        Optional<Reporting> result = reportingRepository.findById(id);

        if(result.isEmpty()) {
            throw new RuntimeException("Did not find reporting id - " + id);
        }

        return result.get();
    }

    @Override
    public Reporting save(Reporting reporting) {
        return reportingRepository.save(reporting);
    }

    @Override
    public void deleteById(long id) {
        reportingRepository.deleteById(id);
    }


}
