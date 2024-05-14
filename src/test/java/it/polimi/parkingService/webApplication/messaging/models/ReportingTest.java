package it.polimi.parkingService.webApplication.messaging.models;

import it.polimi.parkingService.webApplication.account.models.User;
import it.polimi.parkingService.webApplication.messaging.enums.ReportingSeverity;
import it.polimi.parkingService.webApplication.messaging.exceptions.AddingResponseForbidden;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ReportingTest {
    private Reporting reporting;
    private Response response;
    private User author;

    @BeforeEach
    public void setUp() {
        author = new User();
        reporting = new Reporting(LocalDateTime.now(), "testHeading", "testBody", author, ReportingSeverity.HIGH, true);
        response = new Response();
    }

    @Test
    public void testConstructorAndGetters() {
        LocalDateTime timestamp = LocalDateTime.now();
        String heading = "testHeading";
        String body = "testBody";
        ReportingSeverity severity = ReportingSeverity.HIGH;
        boolean open = true;

        Reporting reporting = new Reporting(timestamp, heading, body, author, severity, open);

        assertEquals(timestamp, reporting.getTimestamp());
        assertEquals(heading, reporting.getHeading());
        assertEquals(body, reporting.getBody());
        assertEquals(author, reporting.getAuthor());
        assertEquals(severity, reporting.getSeverity());
        assertTrue(reporting.isOpen());
    }

    @Test
    public void testSetters() {
        ReportingSeverity severity = ReportingSeverity.LOW;
        boolean open = false;
        List<Response> responses = new ArrayList<>();

        reporting.setSeverity(severity);
        reporting.setOpen(open);
        reporting.setLinkedResponses(responses);

        assertEquals(severity, reporting.getSeverity());
        assertFalse(reporting.isOpen());
        assertEquals(responses, reporting.getLinkedResponses());
    }

    @Test
    public void testAddResponse_Success() throws AddingResponseForbidden {
        reporting.addResponse(response);

        assertNotNull(reporting.getLinkedResponses());
        assertTrue(reporting.getLinkedResponses().contains(response));
        assertEquals(reporting, response.getReportingFirstMessage());
    }

    @Test
    public void testAddResponse_ThreadClosed() {
        reporting.setOpen(false);

        assertThrows(AddingResponseForbidden.class, () -> {
            reporting.addResponse(response);
        });
    }

    @Test
    public void testDefaultConstructor() {
        Reporting reporting = new Reporting();

        assertNull(reporting.getTimestamp());
        assertNull(reporting.getHeading());
        assertNull(reporting.getBody());
        assertNull(reporting.getAuthor());
        assertNull(reporting.getSeverity());
        assertTrue(reporting.isOpen());
        assertNull(reporting.getLinkedResponses());
    }
}
