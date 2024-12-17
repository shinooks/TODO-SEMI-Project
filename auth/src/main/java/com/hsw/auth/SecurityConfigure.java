package com.hsw.auth;

import ch.qos.logback.core.net.SyslogOutputStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Optional;

@EnableWebSecurity
@Configuration
public class SecurityConfigure {
    @Bean
    public JWTProvider jwtProvider(){
        return new JWTProvider();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain basicChain(HttpSecurity http) throws Exception {
        http.csrf(csrf->csrf.disable());
        http.sessionManagement(session->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        // http.addFilterBefore(new JWTFilter(jwtProvider()), UsernamePasswordAuthenticationFilter.class );
        http.formLogin(form->
                form
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .successHandler((request, response, authentication) -> {
                        System.out.println("인증에 성공함.");
                        Optional.of(request.getInputStream().readAllBytes()).ifPresent(
                                input->{System.out.println(input);});
                        response.setStatus(201);
                        System.out.println("이름 정보 가져오기.");
                        Optional.of(authentication.getName())
                                .ifPresent(name-> {
                                    System.out.println("authentication Name:"+name);
                                    System.out.println("인증 토큰 반환하기!!");
                                    String token = jwtProvider().GenerateToken(authentication);
                                    System.out.println("token:"+token);
                                    response.setHeader("Authorization","Bearer "+token);
                                });

                        System.out.println("Principal 가져오기!");
                        Optional.of(authentication.getPrincipal()).ifPresent(principal->
                                System.out.println("Principal: "+principal));

                    })
                    .failureHandler((request, response, exception) -> {
                        System.out.println("인증에 실패했음.");
                        System.out.println("입력된 패스워드:"+request.getParameter("password"));
                        System.out.println(exception.getMessage());
                        response.setStatus(401);
                    })
        );
        http.authorizeHttpRequests(request->
                request.requestMatchers("/auth/login","/auth/join").permitAll()
                        .anyRequest().authenticated()
        );
        return http.build();
    }

}
