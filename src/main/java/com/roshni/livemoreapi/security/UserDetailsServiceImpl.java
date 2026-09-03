package com.roshni.livemoreapi.security;

import com.roshni.livemoreapi.entity.User;
import com.roshni.livemoreapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email : " + email));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities("ROLE_" + user.getRole().name())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(user.isDeleted())
                .build();

    }

    public UserDetails loadUserById(Long id) {
        User user = userService.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id : " + id));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities("ROLE_" + user.getRole().name())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(user.isDeleted())
                .build();
    }
}
