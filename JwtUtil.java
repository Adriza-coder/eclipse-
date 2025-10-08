package com.example.demo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class JwtUtil {

    // Static secret key to keep tokens valid across restarts
    private final String SECRET = "MySuperSecretKeyForJWTThatIsLongEnough12345"; 
    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

    // Token validity (1 hour)
    private final long EXPIRATION_TIME = 60 * 60 * 1000;

    // Generate token
    public String generateToken(String username, Set<String> roles) {
        return Jwts.builder()
                .setSubject(username)
                .claim("roles", roles) // store roles as a collection
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    // Extract username
    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    // Extract roles
    public Set<String> extractRoles(String token) {
        Object rolesObject = getClaims(token).get("roles");
        Set<String> roles = new HashSet<>();
        if (rolesObject instanceof List<?>) {
            ((List<?>) rolesObject).forEach(r -> roles.add(r.toString()));
        }
        return roles;
    }

    // Validate token
    public boolean validateToken(String token, String username) {
        String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
