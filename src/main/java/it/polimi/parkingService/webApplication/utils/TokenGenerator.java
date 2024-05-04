package it.polimi.parkingService.webApplication.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class TokenGenerator {

    @Value("${jwt.secret}")
    private String SECRET = "default";

    @Value("${jwt.expiration}")
    private long SECONDS_TO_EXPIRE;
    private final Algorithm algorithm;

    public TokenGenerator() {
        algorithm = Algorithm.HMAC256(SECRET);
    }
    public String getToken(String claimName, Long claimValue) {
        return JWT.create().withClaim(claimName, claimValue).withExpiresAt(Instant.now().plusSeconds(SECONDS_TO_EXPIRE)).sign(algorithm);
    }

    public Integer decodeToken(String claimName,String token) {
        DecodedJWT jwt = JWT.require(algorithm).build().verify(token);
        return jwt.getClaim(claimName).asInt();
    }
}
