package com.example.security0912.auth.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecureDigestAlgorithm;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.Authentication;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.Map;

public class JWTProvider {
    private final String secretKey = "asdsadsadqwxzvzxca";
    private Date currentTime = new Date(System.currentTimeMillis());
    private Date expired = new Date(System.currentTimeMillis() + 3600 * 1000);

    private SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());

    public String TokenGenerator(Authentication authentication) {
        return Jwts.builder()
                .header().add(Map.of(
                        "alg","HS256",
                        "typ","JWT"))
                        .and()
                .claims(Map.of(
                        "isu","HSW",
                        "sub",authentication.getName(),
                        "isuAt", currentTime,
                        "exp",expired,
                        "TOKEN_TYPE","ACCESS_TOKEN"
                ))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean InvalidToken(String token) {
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(key)
                                    .build()
                .parseClaimsJws(token);
        System.out.println("1. 입력된 토큰을 검증함");
        System.out.println("토큰 발급자 검증:"+ claimsJws.getPayload().getIssuer().equals("HSW"));
        if(!claimsJws.getPayload().getIssuer().equals("HSW")){
            System.out.println("잘못된 토큰 공급처");
            return false;
        }
        System.out.println("2. 토큰 유효기간을 검증함");
        if(claimsJws.getPayload().getIssuedAt().after(currentTime)
        && claimsJws.getPayload().getExpiration().before(expired)){
            System.out.println("토큰이 유효함.");
            return true;
        } else {
            System.out.println("토큰이 유효하지 않음.");
            return false;
        }
    }

}
