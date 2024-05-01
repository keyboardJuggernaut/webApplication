package it.polimi.parkingService.webApplication.messaging.models;

import it.polimi.parkingService.webApplication.account.models.UserAccount;

import java.time.LocalDateTime;

public class Response {
    private LocalDateTime timestamp;
    private String body;
    private UserAccount author;

    private Message firstMessage;

    public Response(LocalDateTime timestamp, String body, UserAccount author) {
        this.timestamp = timestamp;
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

    public UserAccount getAuthor() {
        return author;
    }

    public void setAuthor(UserAccount author) {
        this.author = author;
    }

    public Message getFirstMessage() {
        return firstMessage;
    }

    public void setFirstMessage(Message firstMessage) {
        this.firstMessage = firstMessage;
    }
}
