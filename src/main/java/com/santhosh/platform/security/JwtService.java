package com.santhosh.platform.security;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    private final SecretKey signingKey;
    private final long expirationMinutes;

    public JwtService(@Value("${app.security.jwt.secret}") String base64Secret,
                      @Value("${app.security.jwt.expiration-minutes}") long expirationMinutes) {
        byte[] keyBytes;
        try {
            keyBytes = Base64.getDecoder().decode(base64Secret);
        } catch (IllegalArgumentException ex) {
            keyBytes = base64Secret.getBytes(StandardCharsets.UTF_8);
        }
        if (keyBytes.length < 32) {
            throw new IllegalArgumentException("JWT secret must contain at least 256 bits");
        }
        this.signingKey = new SecretKeySpec(keyBytes, "HmacSHA256");
        this.expirationMinutes = expirationMinutes;
    }

    public String generateToken(UserDetails user) {
        Instant now = Instant.now();
        return Jwts.builder()
                .subject(user.getUsername())
                .claim("roles", user.getAuthorities().stream().map(a -> a.getAuthority()).toList())
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(expirationMinutes * 60)))
                .signWith(signingKey)
                .compact();
    }

    public String extractUsername(String token) {
        return claims(token).getSubject();
    }

    public boolean isValid(String token, UserDetails user) {
        Claims claims = claims(token);
        return user.getUsername().equals(claims.getSubject()) && claims.getExpiration().after(new Date());
    }

    private Claims claims(String token) {
        return Jwts.parser().verifyWith(signingKey).build().parseSignedClaims(token).getPayload();
    }
}