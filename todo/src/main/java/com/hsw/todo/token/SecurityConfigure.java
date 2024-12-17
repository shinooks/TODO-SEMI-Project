package com.hsw.todo.token;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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
        http.formLogin(form-> form.disable());

        http.sessionManagement(session->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(new JWTFilter(jwtProvider()), UsernamePasswordAuthenticationFilter.class );

        http.authorizeHttpRequests(request->
                request.anyRequest().authenticated()
        );
        return http.build();
    }

}
