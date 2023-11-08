package com.example.enkatapp.security;

import com.example.enkatapp.models.AppUser;
import com.example.enkatapp.repositories.AppUserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AppUserRepository appUserRepository;

    @Autowired
    public CustomUserDetailsService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        // Konvertera AppUser:s roll till en Spring Security GrantedAuthority
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(appUser.getRole().name());

        // Returnera en User (från org.springframework.security.core.userdetails.User) med
        // användarnamnet, lösenordet och behörigheterna som UserDetails
        return new User(appUser.getUsername(), appUser.getPassword(), Collections.singletonList(authority));
    }
}