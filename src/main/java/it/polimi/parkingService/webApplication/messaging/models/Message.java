package it.polimi.parkingService.webApplication.messaging.models;

import it.polimi.parkingService.webApplication.account.models.User;
import it.polimi.parkingService.webApplication.messaging.exceptions.AddingResponseForbidden;
import jakarta.persistence.*;

import java.time.LocalDateTime;

//@Entity
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@MappedSuperclass
public abstract class Message {

//    @Id
//    @GeneratedValue(strategy = GenerationType.TABLE, generator = "message_generator")
//    @TableGenerator(name = "message_generator", table = "id_generator", pkColumnName = "gen_name", valueColumnName = "gen_value", pkColumnValue = "message_id", allocationSize = 1)
//    @Column(name = "id")
//    private Long id;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="timestamp")
    private LocalDateTime timestamp;
    @Column(name="heading")
    private String heading;
    @Column(name="body")
    private String body;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User author;

    public abstract void addResponse(Response response) throws AddingResponseForbidden;

    public Message(LocalDateTime timestamp, String heading, String body, User author) {
        this.timestamp = timestamp;
        this.body = body;
        this.author = author;
        this.heading = heading;
    }

    public Message(){}

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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }
}
