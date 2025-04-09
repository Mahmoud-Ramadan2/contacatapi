package com.example.contacatapi.contacatapi.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtil {
    @Value("${JWTSecretKey}")
    private String jwtSecretKey;
    @Value("${JWTExpiration}")
    private long expirationTime;

    public String generateToken(String email) {
        return JWT.create()
                .withSubject("contentapi")
                .withIssuer("mahmoud/contact")
                .withClaim("email", email)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
                .sign(Algorithm.HMAC256(jwtSecretKey))
                ;
    }

    public String validateTokenAndRetrieveEmail(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(jwtSecretKey))
                    .withSubject("contentapi")
                    .withIssuer("mahmoud/contact")
                    .build();
            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT.getClaim("email").asString();
        } catch (JWTVerificationException e) {
            throw new RuntimeException("Invalid or expired JWT token", e);
        }
    }
}
