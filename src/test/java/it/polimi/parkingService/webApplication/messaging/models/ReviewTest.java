package it.polimi.parkingService.webApplication.messaging.models;

import it.polimi.parkingService.webApplication.account.models.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ReviewTest {
    @Test
    public void testConstructorAndGetters() {
        LocalDateTime timestamp = LocalDateTime.now();
        String heading = "Test Heading";
        String body = "Test Body";
        User author = new User();
        int starsNumber = 5;

        Review review = new Review(timestamp, heading, body, author, starsNumber);

        assertEquals(timestamp, review.getTimestamp());
        assertEquals(heading, review.getHeading());
        assertEquals(body, review.getBody());
        assertEquals(author, review.getAuthor());
        assertEquals(starsNumber, review.getStarsNumber());
    }

    @Test
    public void testSetters() {
        Review review = new Review();
        int starsNumber = 4;

        review.setStarsNumber(starsNumber);

        assertEquals(starsNumber, review.getStarsNumber());
    }

    @Test
    public void testDefaultConstructor() {
        Review review = new Review();

        assertNull(review.getTimestamp());
        assertNull(review.getHeading());
        assertNull(review.getBody());
        assertNull(review.getAuthor());
        assertEquals(0, review.getStarsNumber());
    }
}
