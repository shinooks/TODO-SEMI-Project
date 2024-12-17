package com.example.security0912.auth;

import com.example.security0912.auth.jwt.JWTAuthenticationFilter;
import com.example.security0912.auth.jwt.JWTProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigure {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain LoginFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf->csrf.disable());
        http.cors(Customizer.withDefaults());
        http.addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.sessionManagement(session->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.authorizeHttpRequests(req->
                req.requestMatchers("/auth/**").permitAll()
                    .anyRequest().authenticated());
        http.formLogin(form->
                form.loginPage("/auth/login")
                    .loginProcessingUrl("/auth/login")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .successHandler((request, response, authentication) -> {
                            System.out.println("인증 성공");
                            JWTProvider jwtProvider = new JWTProvider();
                            String token = jwtProvider.TokenGenerator(authentication);
                            response.addHeader("Authorization", "Bearer" + token);
                            System.out.println("토큰 생성됨"+ token);
                        })
                    .failureHandler((request, response, exception) -> {
                        System.out.println("인증 실패");
                        response.setStatus(401);
                    }));

        return http.build();
    }
}
