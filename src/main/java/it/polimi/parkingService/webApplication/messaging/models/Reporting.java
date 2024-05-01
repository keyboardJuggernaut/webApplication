package it.polimi.parkingService.webApplication.messaging.models;

import it.polimi.parkingService.webApplication.account.models.UserAccount;
import it.polimi.parkingService.webApplication.messaging.enums.ReportingSeverity;
import it.polimi.parkingService.webApplication.messaging.enums.ReportingStatus;
import it.polimi.parkingService.webApplication.messaging.exceptions.AddingResponseForbidden;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Reporting extends Message {
    private ReportingSeverity severity;
    private ReportingStatus status = ReportingStatus.OPEN;
    private List<Response> linkedResponses;

    public Reporting(LocalDateTime timestamp, String body, UserAccount author, Forum forum, ReportingSeverity severity) {
        super(timestamp, body, author, forum);
        this.severity = severity;
    }

    @Override
    public void addResponse(Response response) throws AddingResponseForbidden {
        if(linkedResponses == null){
            linkedResponses = new ArrayList<>();
        }
        if(status == ReportingStatus.CLOSED) {
            throw new AddingResponseForbidden("The thread has been closed");
        }
        linkedResponses.add(response);
        response.setFirstMessage(this);
    }

    public ReportingSeverity getSeverity() {
        return severity;
    }

    public void setSeverity(ReportingSeverity severity) {
        this.severity = severity;
    }

    public ReportingStatus getStatus() {
        return status;
    }

    public void setStatus(ReportingStatus status) {
        this.status = status;
    }
}
