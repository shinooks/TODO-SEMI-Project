package com.hsw.todo.token;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {
    private final JWTProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getHeader("Authorization")!=null && request.getHeader("Authorization").startsWith("Bearer ")) {
           String token = request.getHeader("Authorization").substring(7);
           System.out.println("토큰이 입력되었습니다.:"+token);
           if(jwtProvider.VerifyToken(token) && jwtProvider.GetAuthentication(token)!=null) {
               System.out.println("토큰으로 인증 되었습니다.");
               response.setStatus(201);
           };
        }

        filterChain.doFilter(request, response);
    }
}
