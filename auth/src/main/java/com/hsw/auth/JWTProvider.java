package com.hsw.auth;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.impl.JWTParser;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import org.springframework.cglib.core.HashCodeCustomizer;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class JWTProvider {
    private static final String SECRET_KEY = String.valueOf("my_secret_key".hashCode());
    public String GenerateToken(Authentication authentication) {
        String name = authentication.getName();
        String token = JWT.create()
                .withHeader(Map.of(
                        "alg","HS256",
                        "typ","JWT"))
                .withSubject(name)
                .withIssuer("HSW")
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
                .withClaim("role", "USER")
                .sign(Algorithm.HMAC256(SECRET_KEY));
        return token;
    }

    public boolean VerifyToken(String token) {
        try {
            DecodedJWT verifiedToken = JWT.require(Algorithm.HMAC256(SECRET_KEY)).build().verify(token);
            if(!verifiedToken.getIssuer().equals("HSW")) {
                throw new JWTVerificationException("Issuer Invalid token");
            };
            if(verifiedToken.getIssuedAt().after(new Date(System.currentTimeMillis()))
            ||verifiedToken.getExpiresAt().before(new Date(System.currentTimeMillis()))){
                System.out.println("현재 시간: " + new Date(System.currentTimeMillis()));
                System.out.println("시작 시간: "+ verifiedToken.getIssuedAt());
                System.out.println("만료 시간: "+ verifiedToken.getExpiresAt());
                throw new JWTVerificationException("Time Invalid token");
            }
        }catch(JWTVerificationException e) {
            System.out.println("잘못된 토큰 입니다."+e.getMessage());
            return false;
        }
        return true;
    }

    public Authentication GetAuthentication(String token) {
        String username = JWT.decode(token).getSubject();
        String role = JWT.decode(token).getClaim("role").asString();
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, null, List.of(new SimpleGrantedAuthority(role)));
        // Security Context에 Authentication 객체를 등록
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        return authenticationToken;

    }
}
