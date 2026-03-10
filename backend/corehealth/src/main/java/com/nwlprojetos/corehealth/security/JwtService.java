package com.nwlprojetos.corehealth.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    private final SecretKey secretKey;
    private final long expirationSeconds;

    public JwtService(@Value("${application.security.jwt.secret:corehealth-super-secret-key-corehealth-super-secret-key}") String secret,
                      @Value("${application.security.jwt.expiration-seconds:3600}") long expirationSeconds) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationSeconds = expirationSeconds;
    }

    public String generateToken(AppUserDetails userDetails, String tenantSlug) {
        Instant now = Instant.now();
        List<String> roles = userDetails.getAuthorities().stream().map(authority -> authority.getAuthority()).toList();
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claims(Map.of("tenantId", userDetails.getTenantId(), "tenantSlug", tenantSlug, "roles", roles, "email", userDetails.getEmail()))
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(expirationSeconds)))
                .signWith(secretKey)
                .compact();
    }

    public String extractUsername(String token) {
        return parseClaims(token).getSubject();
    }

    public String extractTenantSlug(String token) {
        return parseClaims(token).get("tenantSlug", String.class);
    }

    public boolean isTokenValid(String token, AppUserDetails userDetails) {
        return extractUsername(token).equals(userDetails.getUsername())
                && parseClaims(token).getExpiration().after(new Date());
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
