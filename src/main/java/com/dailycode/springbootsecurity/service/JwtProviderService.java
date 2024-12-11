package com.dailycode.springbootsecurity.service;

import com.dailycode.springbootsecurity.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtProviderService {

    private String secretkey;

    public String getSecretkey() {
        return secretkey = "41693884e1efb88ecf33649fe4eb76788205152611bdd2e57aa5beab3e24094f";
    }

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts
                .builder()
                .claims()
                .add(claims)
                .subject(user.getUsername())
                .issuer("MAJ")
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + (60 * 10 * 1000)))
                .and()
                .signWith(generateKey())
                .compact();
    }

    private SecretKey generateKey() {
        byte[] bytes = Decoders.BASE64.decode(getSecretkey());
        return Keys.hmacShaKeyFor(bytes);
    }
}
