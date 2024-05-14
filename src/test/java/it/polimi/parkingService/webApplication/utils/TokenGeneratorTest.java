package it.polimi.parkingService.webApplication.utils;

import com.auth0.jwt.exceptions.TokenExpiredException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TokenGeneratorTest {
    private TokenGenerator tokenGenerator;

    @BeforeEach
    public void setUp() {
        tokenGenerator = Mockito.spy(new TokenGenerator());
        Mockito.doReturn(3600L).when(tokenGenerator).getSECONDS_TO_EXPIRE();
    }

    @Test
    public void testGetTokenAndDecodeToken() throws TokenExpiredException {
        String claimName = "userId";
        Long claimValue = 12345L;

        String token = tokenGenerator.getToken(claimName, claimValue);
        assertThrows(TokenExpiredException.class, () -> tokenGenerator.decodeToken(claimName, token));
    }

    @Test
    public void testDecodeToken_InvalidToken() {
        String invalidToken = "invalidToken";
        String claimName = "userId";

        assertThrows(com.auth0.jwt.exceptions.JWTVerificationException.class, () -> {
            tokenGenerator.decodeToken(claimName, invalidToken);
        });
    }
}
