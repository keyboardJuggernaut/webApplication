package it.polimi.parkingService.webApplication.utils;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@Component
public class QRCodeGenerator {
    public static String getQRCodeEncodedImage(String text, int width, int height) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageConfig con = new MatrixToImageConfig( 0xFF000002 , 0xFFFFC041 ) ;

        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream,con);
        byte[] pngData = pngOutputStream.toByteArray();
        return Base64.getEncoder().encodeToString(pngData);
    }

    public static String decodeQRCodeEncodedImage(String base64QRCode) throws NotFoundException, IOException {
        // Decode Base64 string to byte array
        byte[] qrCodeBytes = Base64.getDecoder().decode(base64QRCode);

        // Create BinaryBitmap from byte array
        ByteArrayInputStream inputStream = new ByteArrayInputStream(qrCodeBytes);
        BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(ImageIO.read(inputStream));
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

        // Decode QR code
        MultiFormatReader reader = new MultiFormatReader();
        Result result = reader.decode(bitmap);

        // Return decoded content
        return result.getText();
    }
}
