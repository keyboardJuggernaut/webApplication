package it.polimi.parkingService.webApplication.messaging.services;

import it.polimi.parkingService.webApplication.messaging.exceptions.AddingResponseForbidden;
import it.polimi.parkingService.webApplication.messaging.exceptions.InvalidThreadStatus;
import it.polimi.parkingService.webApplication.messaging.models.Reporting;
import it.polimi.parkingService.webApplication.messaging.models.Response;
import it.polimi.parkingService.webApplication.common.BaseService;

public interface IReportingService extends BaseService<Reporting> {
    Reporting completeReporting(Reporting reporting, String username);

    void updateThread(long id) throws InvalidThreadStatus;

    void addResponseToReporting(long reportingId, Response response, String username) throws AddingResponseForbidden;
}
