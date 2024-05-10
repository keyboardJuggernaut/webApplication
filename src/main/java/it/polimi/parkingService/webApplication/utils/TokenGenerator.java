package it.polimi.parkingService.webApplication.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;

/**
 * The {@code TokenGenerator} handles any jwt related business logic
 */
@Component
public class TokenGenerator {

    @Value("${jwt.secret}")
    private String SECRET = "default";

    @Value("${jwt.expiration}")
    private long SECONDS_TO_EXPIRE;

    private final Algorithm algorithm;

    /**
     * Constructs the generator
     */
    public TokenGenerator() {
        // set the default encrypting algorithm
        algorithm = Algorithm.HMAC256(SECRET);
    }

    /**
     * Generates a jwt with a key-value payload
     * @param claimName the key name
     * @param claimValue the value
     * @return jwt string representation
     */
    public String getToken(String claimName, Long claimValue) {
        return JWT.create().withClaim(claimName, claimValue).withExpiresAt(Instant.now().plusSeconds(SECONDS_TO_EXPIRE)).sign(algorithm);
    }

    /**
     * Verifies and decodes the token for payload retrieval
     * @param claimName the key name
     * @param token the jwt
     * @return value
     */
    public Long decodeToken(String claimName,String token) {
        DecodedJWT jwt = JWT.require(algorithm).build().verify(token);
        return jwt.getClaim(claimName).asLong();
    }
}
