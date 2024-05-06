package it.polimi.parkingService.webApplication.messaging.models;

import it.polimi.parkingService.webApplication.account.models.User;
import it.polimi.parkingService.webApplication.messaging.enums.ReportingSeverity;
import it.polimi.parkingService.webApplication.messaging.enums.ReportingStatus;
import it.polimi.parkingService.webApplication.messaging.exceptions.AddingResponseForbidden;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="reporting")
public class Reporting extends Message {

    @Column(name="severity")
    @Enumerated(EnumType.STRING)
    private ReportingSeverity severity;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private ReportingStatus status = ReportingStatus.OPEN;

    @OneToMany(
            mappedBy = "reportingFirstMessage",
            fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    private List<Response> linkedResponses;

    public Reporting(LocalDateTime timestamp, String body, User author, Forum forum, ReportingSeverity severity) {
        super(timestamp, body, author, forum);
        this.severity = severity;
    }

    public Reporting() {

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
        response.setReportingFirstMessage(this);
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


    public List<Response> getLinkedResponses() {
        return linkedResponses;
    }

    public void setLinkedResponses(List<Response> linkedResponses) {
        this.linkedResponses = linkedResponses;
    }
}
