package it.polimi.parkingService.webApplication.messaging.models;

import it.polimi.parkingService.webApplication.account.UserAccount;
import it.polimi.parkingService.webApplication.messaging.exceptions.AddingResponseForbidden;

import java.time.LocalDateTime;

public abstract class Message {
    private LocalDateTime timestamp;
    private String body;
    private UserAccount author;
    private Forum forum;
    public abstract void addResponse(Response response) throws AddingResponseForbidden;

    public Message(LocalDateTime timestamp, String body, UserAccount author, Forum forum) {
        this.timestamp = timestamp;
        this.body = body;
        this.author = author;
        this.forum = forum;
    }

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

    public UserAccount getAuthor() {
        return author;
    }

    public void setAuthor(UserAccount author) {
        this.author = author;
    }

    public Forum getForum() {
        return forum;
    }

    public void setForum(Forum forum) {
        this.forum = forum;
    }
}
