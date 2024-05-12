package it.polimi.parkingService.webApplication.parking.services;

import com.google.zxing.WriterException;
import it.polimi.parkingService.webApplication.utils.QRCodeGenerator;
import it.polimi.parkingService.webApplication.utils.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * The {@code QRCodeService} handles any qr code related business logic
 */
@Service
public class QRCodeService implements IQRCodeService {
    private final TokenGenerator tokenGenerator;

    /**
     * Constructs the services
     * @param tokenGenerator the class handling jwt related business logic
     */
    @Autowired
    public QRCodeService(TokenGenerator tokenGenerator) {
        this.tokenGenerator = tokenGenerator;
    }

    /**
     * Generates qrcode with embedded jwt including a key-value payload
     * @param key name
     * @param id payload
     * @return qr code representation
     * @throws IOException if IO fails
     * @throws WriterException if writing fails
     */
    public String getQRCodeWithEmbeddedToken(String key, Long id) throws IOException, WriterException {
        String token = tokenGenerator.getToken(key, id);
        return QRCodeGenerator.getQRCodeEncodedImage(token, 100, 100);
    }

    /**
     * Retrieves token content from key reference
     * @param claimName the key name
     * @param token the encrypted token
     * @return the value
     */
    public Long getTokenPayload(String claimName, String token) {
        return tokenGenerator.decodeToken(claimName, token);
    }

}
