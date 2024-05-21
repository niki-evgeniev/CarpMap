package com.example.carpmap.Service.Impl;

import com.example.carpmap.Models.Entity.User;
import com.example.carpmap.Models.Entity.UserRole;
import com.example.carpmap.Repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


import static com.example.carpmap.Cammon.SuccessfulMessages.SUCCESSFUL_LOGIN;

public class CarpUserService implements UserDetailsService {

    private final UserRepository userRepository;

    public CarpUserService(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = userRepository.findByUsername(username)
                .map(CarpUserService::map)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found"));
        System.out.printf(SUCCESSFUL_LOGIN, userDetails.getUsername());
        return userDetails;
    }

    private static UserDetails map(User user) {
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getRoles()
                        .stream()
                        .map(CarpUserService::map)
                        .toList())
                .build();
    }

    private static GrantedAuthority map(UserRole userRole) {
        return new SimpleGrantedAuthority("ROLE_" + userRole.getRoleType().name());
    }
}
