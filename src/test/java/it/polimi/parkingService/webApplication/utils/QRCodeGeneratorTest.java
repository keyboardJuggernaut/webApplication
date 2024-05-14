package it.polimi.parkingService.webApplication.utils;

import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import jakarta.validation.constraints.Null;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class QRCodeGeneratorTest {
    @Test
    public void testGetQRCodeEncodedImage() throws WriterException, IOException, NotFoundException {
        String text = "Test QR Code";
        int width = 200;
        int height = 200;

        String qrCodeImage = QRCodeGenerator.getQRCodeEncodedImage(text, width, height);
        String decodedText = QRCodeGenerator.decodeQRCodeEncodedImage(qrCodeImage);

        assertEquals(text, decodedText);
    }

    @Test
    public void testDecodeQRCodeEncodedImage_InvalidQRCode() {
        String invalidQRCode = "InvalidQRCodeData";

        assertThrows(IllegalArgumentException.class, () -> QRCodeGenerator.decodeQRCodeEncodedImage(invalidQRCode));
    }

    @Test
    public void testDecodeQRCodeEncodedImage_NotFoundException() {
        String invalidQRCode = Base64.getEncoder().encodeToString(new byte[]{0, 0, 0, 0});

        assertThrows(NullPointerException.class, () -> QRCodeGenerator.decodeQRCodeEncodedImage(invalidQRCode));
    }
}
