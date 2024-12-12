package com.dailycode.springbootsecurity.service;

import com.dailycode.springbootsecurity.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtProviderService {

    private String secretKey = null;

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder().claims().add(claims).subject(user.getUsername()).issuer("MAJ").issuedAt(new Date(System.currentTimeMillis())).expiration(new Date(System.currentTimeMillis() + 60)).and().signWith(generateKey()).compact();
    }

    private SecretKey generateKey() {
        byte[] decode = Decoders.BASE64.decode(getSecretKey());

        return Keys.hmacShaKeyFor(decode);
    }


    public String getSecretKey() {
        return secretKey = "41693884e1efb88ecf33649fe4eb76788205152611bdd2e57aa5beab3e24094f";
    }

    public String extractUserName(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimResolver) {
        Claims claims = extractClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractClaims(String token) {
        return Jwts.parser().verifyWith(generateKey()).build().parseSignedClaims(token).getPayload();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }
}
