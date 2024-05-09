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

    @Column(name="open")
    private boolean open = true;

    @OneToMany(
            mappedBy = "reportingFirstMessage",
            fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    private List<Response> linkedResponses;

    public Reporting(LocalDateTime timestamp, String heading, String body, User author, Forum forum, ReportingSeverity severity, boolean open) {
        super(timestamp, heading, body, author, forum);
        this.severity = severity;
        this.open = open;
    }

    public Reporting() {

    }

    @Override
    public void addResponse(Response response) throws AddingResponseForbidden {
        if(linkedResponses == null){
            linkedResponses = new ArrayList<>();
        }
        if(!open) {
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

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public List<Response> getLinkedResponses() {
        return linkedResponses;
    }

    public void setLinkedResponses(List<Response> linkedResponses) {
        this.linkedResponses = linkedResponses;
    }
}
