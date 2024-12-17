package com.hsw.auth;

import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/auth/join")
    public ResponseEntity<User> join(User user) {
        if (userRepository.existsByUsername(user.getUsername())) return ResponseEntity.status(409).build();
        String pwEncode = passwordEncoder.encode(user.getPassword());
        user.setPassword(pwEncode);
        User result = userRepository.save(user);
        if (result == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(result);
    }
}
