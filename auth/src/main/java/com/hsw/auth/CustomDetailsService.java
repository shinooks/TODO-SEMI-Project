package com.hsw.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = null;
        try {
            user = Optional.of(repo.findByUsername(username))
                    .orElseThrow(()->new UsernameNotFoundException(username));
        }catch(UsernameNotFoundException e) {
            System.out.println("유저가 확인되지 않았음" +e);
            throw new UsernameNotFoundException("사용자 없음.");
        }
    return new CustomUserDetails(user);}
}
