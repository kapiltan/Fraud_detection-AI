package com.fraudintel.fraudDetection.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {
    private final SecretKey key;
    private final long expirationMs;

    public JwtService(
            @Value("${app.jwt.secret}")String secret,
            @Value("${app.jwt.expiration-ms}") long expirationMs
    ){
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.expirationMs = expirationMs;
    }

    public String generateToken(String subject, Map<String, Object> claims){
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expirationMs);
        
        return Jwts.builder().claims(claims).subject(subject).issuedAt(now).expiration(expiry).signWith(key).compact();
    }

    public Jws<Claims> validateToken(String token){
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
    }
}
