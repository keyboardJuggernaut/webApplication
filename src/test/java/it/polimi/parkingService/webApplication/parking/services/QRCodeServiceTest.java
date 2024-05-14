package it.polimi.parkingService.webApplication.parking.services;

import com.google.zxing.WriterException;
import it.polimi.parkingService.webApplication.utils.QRCodeGenerator;
import it.polimi.parkingService.webApplication.utils.TokenGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class QRCodeServiceTest {

    @InjectMocks
    private QRCodeService qrCodeService;

    @Mock
    private TokenGenerator tokengenerator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetQRCodeWithEmbeddedToken() throws IOException, WriterException {
        // Mock
        String token = "mockedToken";
        when(tokengenerator.getToken(anyString(), anyLong())).thenReturn(token);
        String expectedQRCode = "mockedQRCode";

        String actualQRCode = qrCodeService.getQRCodeWithEmbeddedToken("testKey", 123L);

        assertNotNull(expectedQRCode);
    }

    @Test
    void testGetTokenPayload() {
        String claimName = "mockedClaim";
        String token = "mockedToken";
        long expectedPayload = 456L;
        when(tokengenerator.decodeToken(claimName, token)).thenReturn(expectedPayload);

        long actualPayload = qrCodeService.getTokenPayload(claimName, token);

        assertEquals(expectedPayload, actualPayload);
    }
}
