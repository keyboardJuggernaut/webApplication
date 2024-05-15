package it.polimi.parkingService.webApplication.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ErrorResponseTest {
    @Test
    public void testConstructorAndGetters() {
        ErrorResponse errorResponse = new ErrorResponse(404, "Not Found", System.currentTimeMillis());
        assertEquals(404, errorResponse.getStatus());
        assertEquals("Not Found", errorResponse.getMessage());

        assertTrue(errorResponse.getTimeStamp() > 0);
    }

    @Test
    public void testSetters() {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(500);
        errorResponse.setMessage("Internal Server Error");
        long timeStamp = System.currentTimeMillis();
        errorResponse.setTimeStamp(timeStamp);

        assertEquals(500, errorResponse.getStatus());
        assertEquals("Internal Server Error", errorResponse.getMessage());

        assertTrue(errorResponse.getTimeStamp() > 0);
    }
}
