package it.polimi.parkingService.webApplication.messaging.models;

import it.polimi.parkingService.webApplication.account.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ResponseTest {
    private Response response;
    private User author;
    private Reporting reporting;

    @BeforeEach
    public void setUp() {
        author = new User();
        reporting = new Reporting();
        response = new Response("testBody", author);
    }

    @Test
    public void testConstructorAndGetters() {
        LocalDateTime now = LocalDateTime.now();

        assertEquals("testBody", response.getBody());
        assertEquals(author, response.getAuthor());
        assertNotNull(response.getTimestamp());
        assertTrue(response.getTimestamp().isBefore(now.plusSeconds(1))); 
    }

    @Test
    public void testSetters() {
        LocalDateTime timestamp = LocalDateTime.now();
        String body = "updateBody";
        User newAuthor = new User();
        Reporting newReporting = new Reporting();

        response.setTimestamp(timestamp);
        response.setBody(body);
        response.setAuthor(newAuthor);
        response.setReportingFirstMessage(newReporting);

        assertEquals(timestamp, response.getTimestamp());
        assertEquals(body, response.getBody());
        assertEquals(newAuthor, response.getAuthor());
        assertEquals(newReporting, response.getReportingFirstMessage());
    }
    @Test
    public void testDefaultConstructor() {
        Response response = new Response();

        assertNull(response.getId());
        assertNull(response.getTimestamp());
        assertNull(response.getBody());
        assertNull(response.getAuthor());
        assertNull(response.getReportingFirstMessage());
    }
}
