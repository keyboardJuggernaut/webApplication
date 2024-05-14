package it.polimi.parkingService.webApplication.parking.services;

import it.polimi.parkingService.webApplication.parking.enums.ParkingSpotStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class SseServiceTest {
    @InjectMocks
    private SseService sseService;

    @Mock
    private SseEmitter emitter;

    private Event event;

    @BeforeEach
    public void setUp() {
        event = new Event(1L, ParkingSpotStatus.BUSY);
    }

    @Test
    public void testAddEmitter() {
        sseService.addEmitter(emitter);

        assertTrue(sseService.getEmitters().contains(emitter));
    }

    @Test
    public void testSendEvent() throws IOException {
        sseService.addEmitter(emitter);

        sseService.sendEvent(event);

//        verify(emitter).send(event);
    }
}
