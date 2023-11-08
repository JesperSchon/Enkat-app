package com.example.enkatapp.services;

import com.example.enkatapp.models.AppUser;
import com.example.enkatapp.repositories.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AppUserService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    public AppUserService(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AppUser createUser(AppUser appUser) {
        String encryptedPassword = passwordEncoder.encode(appUser.getPassword());
        appUser.setPassword(encryptedPassword);
        return appUserRepository.save(appUser);
    }


    public void deleteUser(Long id) {
        appUserRepository.deleteById(id);
    }



    public AppUser findById(Long id) {
        return appUserRepository.findById(id).orElse(null);
    }

    public AppUser save(AppUser appUser) {
        return appUserRepository.save(appUser);
    }
}

