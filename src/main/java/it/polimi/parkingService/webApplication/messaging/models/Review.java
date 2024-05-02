package it.polimi.parkingService.webApplication.messaging.models;

import it.polimi.parkingService.webApplication.account.models.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Review extends Message{

    private int starsNumber;
    private List<Response> linkedResponses;

    public Review(LocalDateTime timestamp, String body, User author, Forum forum, int starsNumber) {
        super(timestamp, body, author, forum);
        this.starsNumber = starsNumber;
    }

    @Override
    public void addResponse(Response response) {
        if(linkedResponses == null){
            linkedResponses = new ArrayList<>();
        }
        linkedResponses.add(response);
        response.setFirstMessage(this);
    }

    public int getStarsNumber() {
        return starsNumber;
    }

    public void setStarsNumber(int starsNumber) {
        this.starsNumber = starsNumber;
    }

    public List<Response> getLinkedResponses() {
        return linkedResponses;
    }

    public void setLinkedResponses(List<Response> linkedResponses) {
        this.linkedResponses = linkedResponses;
    }
}
