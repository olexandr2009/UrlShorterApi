package com.url.shorter.config.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Component
public class JwtUtils {
    @Value("${demo.app.jwtSecret:======================SecretWordForJwt===========================}")
    private String jwtSecret;

    @Value("${demo.app.jwtExpirationMs:86400000}")
    private int jwtExpirationMs;

    public String generateJwtToken(Authentication authentication) {
        if (authentication == null){
            throw new NullPointerException("authentication cannot be null");
        }

        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setId(userPrincipal.getUuid().toString())
                .setSubject((userPrincipal.getUsername()))
                .claim("Authorities", userPrincipal.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    private SecretKey key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public UUID getUserIdFromJwtToken(String token) {
        String id = Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody().getId();
        return Objects.nonNull(id) ? UUID.fromString(id) : null;
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public Claims getUserRolesFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}