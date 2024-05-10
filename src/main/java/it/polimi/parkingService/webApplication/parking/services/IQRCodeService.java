package it.polimi.parkingService.webApplication.parking.services;

import com.google.zxing.WriterException;

import java.io.IOException;

public interface IQRCodeService {
    Long getTokenPayload(String claimName, String token);

    String getQRCodeWithEmbeddedToken(String key, Long id) throws IOException, WriterException;
}
