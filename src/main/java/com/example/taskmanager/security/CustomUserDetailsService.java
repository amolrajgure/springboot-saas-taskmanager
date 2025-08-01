package com.example.taskmanager.security;

import com.example.taskmanager.model.User;
import com.example.taskmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This class implements UserDetailsService, which Spring Security uses to:
 * Load a user from the database by username
 * Pass it to the authentication mechanism (e.g., password matching)
 */

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.isEnabled(),
                true, // accountNonExpired
                true, // credentialsNonExpired
                true, // accountNonLocked
                List.of(new SimpleGrantedAuthority(user.getRole()))
        );
    }
}

/**
 * | Method / Class                          | Purpose                              |
 * | --------------------------------------- | ------------------------------------ |
 * | `loadUserByUsername()`                  | Used by Spring to authenticate       |
 * | `UserDetails` return type               | Converts our `User` to Spring format |
 * | `SimpleGrantedAuthority`                | Converts `role` (e.g., "ROLE\_USER") |
 * | `@Service` + `@RequiredArgsConstructor` | Injects `UserRepository` cleanly     |
 * This class is plugged into the AuthenticationManager and gets used automatically during login.
 */
