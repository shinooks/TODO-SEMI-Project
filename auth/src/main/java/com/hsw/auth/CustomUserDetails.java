package com.hsw.auth;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomUserDetails implements UserDetails {
    @Autowired
    private UserRepository repo;

    private User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        System.out.println("사용자의 권한을 찾습니다.");
        String[] authorities = user.getAuthorities().split(",");
        if (authorities.length > 0) {
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            for (String authority : authorities) {
                System.out.println("권한 부여됨:" + authority);
                grantedAuthorities.add(new SimpleGrantedAuthority(authority));
            }
            return grantedAuthorities;
        } else throw new EntityNotFoundException("잘못된 사용자 입니다.");
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
