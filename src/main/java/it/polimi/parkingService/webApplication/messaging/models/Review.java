package it.polimi.parkingService.webApplication.messaging.models;

import it.polimi.parkingService.webApplication.account.models.User;
import it.polimi.parkingService.webApplication.messaging.exceptions.AddingResponseForbidden;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="review")
public class Review extends Message{

    @Column(name="stars_number")
    private int starsNumber;


    public Review(LocalDateTime timestamp, String heading, String body, User author,  int starsNumber) {
        super(timestamp, heading, body, author);
        this.starsNumber = starsNumber;
    }

    @Override
    public void addResponse(Response response) throws AddingResponseForbidden {

    }

    public Review(){}

    public int getStarsNumber() {
        return starsNumber;
    }

    public void setStarsNumber(int starsNumber) {
        this.starsNumber = starsNumber;
    }

}
