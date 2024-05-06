package it.polimi.parkingService.webApplication.messaging.models;

import it.polimi.parkingService.webApplication.account.models.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="review")
public class Review extends Message{

    @Column(name="stars_number")
    private int starsNumber;

    @OneToMany(
            mappedBy = "reviewFirstMessage",
            fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    private List<Response> linkedResponses;

    public Review(LocalDateTime timestamp, String heading, String body, User author, Forum forum, int starsNumber) {
        super(timestamp, heading, body, author, forum);
        this.starsNumber = starsNumber;
    }

    public Review(){}
    @Override
    public void addResponse(Response response) {
        if(linkedResponses == null){
            linkedResponses = new ArrayList<>();
        }
        linkedResponses.add(response);
        response.setReviewFirstMessage(this);
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
