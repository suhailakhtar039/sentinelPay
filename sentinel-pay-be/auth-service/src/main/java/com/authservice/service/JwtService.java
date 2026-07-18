package com.authservice.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtService {
    private SecretKey secretKey;

    public JwtService(
            @Value("${jwt.secret}")
            String secret
    ) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Generate token with the help of username only.
     * Take care of claims thoroughly
     */
    public String generateToken(Long userId, String email, String role) {
        String jti = UUID.randomUUID().toString();
        return Jwts.builder()
                .setId(jti)
                .setSubject(email)
                .claim("role", role)
                .claim("userId", userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public Long extractUserId(String token) {
        return extractAllClaims(token)
                .get("userId", Long.class);
    }

    /**
     * Validate token. Check username and expiration date.
     */
    public boolean validateToken(String token, String username) {
        String extractedUsername = extractUsername(token);
        return extractedUsername.equals(username) && !isTokenExpired(token);
    }

    /**
     * Extract username. Username is subject in all cases
     */
    public String extractUsername(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getSubject();
    }

    /**
     * Extract role
     */
    public String extractRole(
            String token
    ) {

        Claims claims =
                extractAllClaims(token);

        return claims.get("role", String.class);
    }

    /**
     * Check expiration of token
     */
    private boolean isTokenExpired(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getExpiration()
                .before(new Date());
    }

    /**
     * extract all claims to avoid reusability
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
