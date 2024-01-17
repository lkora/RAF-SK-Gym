package raf.sk.gym.userservice.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private final long jwtExpirationInMillis = 60 * 60 * 1000 * 60;
    private final GymUserDetailsService userDetailsService;
    @Value("${jwt.private_key}")
    private String jwtPrivateKey;
    @Value("${jwt.public_key}")
    private String jwtPublicKey;

    public JwtTokenProvider(GymUserDetailsService userDetailsService) {this.userDetailsService = userDetailsService;}

    public String generateToken(Authentication authentication) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMillis);
        // The only thing ever in Authorities is the Role. So some hackery to get it out.
        String role = authentication.getAuthorities()
                .toArray()[0].toString();
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("role", role)
                .issuer("gym.raf.edu.rs")
                .issuedAt(new Date())
                .expiration(expiryDate)
                .signWith(getPrivateKey(), Jwts.SIG.RS512)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        token = getJwtFromHeader(token);
        Claims claims = Jwts.parser()
                .verifyWith(getPublicKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }

    private String getJwtFromHeader(String header) {
        if (header.startsWith("Bearer")) {
            return header.substring(7);
        }
        return header;
    }

    /**
     * @param jwt JWT to validate
     * @return true if parsing is complete.
     * @throws RuntimeException if an exception occurs when validating the JWT.
     */
    public boolean validateToken(String jwt) {
        var parser = Jwts.parser()
                .verifyWith(getPublicKey())
                .requireIssuer("gym.raf.edu.rs")
                .build();
        parser.parseSignedClaims(jwt);
        return true;
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getPublicKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        String username = claims.getSubject();
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());
    }

    private PrivateKey getPrivateKey() {
        try {
            byte[] keyBytes = Base64.getDecoder()
                    .decode(jwtPrivateKey.getBytes());
            PKCS8EncodedKeySpec X509privateKey = new PKCS8EncodedKeySpec(keyBytes);
            return KeyFactory.getInstance("RSA")
                    .generatePrivate(X509privateKey);
        } catch (Exception ex) {
            throw new AssertionError("Failed to get private key", ex);
        }
    }

    private PublicKey getPublicKey() {
        try {
            byte[] keyBytes = Base64.getDecoder()
                    .decode(jwtPublicKey.getBytes());
            X509EncodedKeySpec X509publicKey = new X509EncodedKeySpec(keyBytes);
            return KeyFactory.getInstance("RSA")
                    .generatePublic(X509publicKey);
        } catch (Exception ex) {
            throw new AssertionError("Failed to get public key", ex);
        }
    }
}