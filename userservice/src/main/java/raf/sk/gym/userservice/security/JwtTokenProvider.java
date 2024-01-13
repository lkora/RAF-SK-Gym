package raf.sk.gym.userservice.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${jwt.secret}")
    private String jwtSecret;
    private final long jwtExpirationInMillis = 60 * 60 * 1000;

    public String generateToken(Authentication authentication) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMillis);
        // The only thing ever in Authorities is the Role. So some hackery to get it out.
        String role = authentication.getAuthorities().toArray()[0].toString();
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("role", role)
                .issuer("gym.raf.edu.rs")
                .issuedAt(new Date())
                .expiration(expiryDate)
                .signWith(getKey(), Jwts.SIG.HS512)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }

    /**
     * @param jwt JWT to validate
     * @return true if parsing is complete.
     * @throws RuntimeException if an exception occurs when validating the JWT.
     */
    public boolean validateToken(String jwt) {
            var parser = Jwts.parser()
                    .verifyWith(getKey())
                    .requireIssuer("gym.raf.edu.rs")
                    .build();
            parser.parseSignedClaims(jwt);
            return true;
    }

    private SecretKey getKey() {
        byte[] keyBytes = Base64.getDecoder()
                .decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}