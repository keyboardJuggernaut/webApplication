package it.polimi.parkingService.webApplication.messaging.models;

import it.polimi.parkingService.webApplication.account.models.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="response")
public class Response {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="timestamp")
    private LocalDateTime timestamp;

    @Column(name="body")
    private String body;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User author;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="review_id")
    private Message reviewFirstMessage;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="reporting_id")
    private Message reportingFirstMessage;

    public Response(String body, User author) {
        this.timestamp = LocalDateTime.now();
        this.body = body;
        this.author = author;
    }

    public Response(){}

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Message getReviewFirstMessage() {
        return reviewFirstMessage;
    }

    public void setReviewFirstMessage(Message reviewFirstMessage) {
        this.reviewFirstMessage = reviewFirstMessage;
    }

    public Message getReportingFirstMessage() {
        return reportingFirstMessage;
    }

    public void setReportingFirstMessage(Message reportingFirstMessage) {
        this.reportingFirstMessage = reportingFirstMessage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
