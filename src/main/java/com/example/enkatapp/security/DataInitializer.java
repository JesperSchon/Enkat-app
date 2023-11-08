package com.example.enkatapp.security;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.example.enkatapp.models.AppUser;
import com.example.enkatapp.models.Role;
import com.example.enkatapp.repositories.AppUserRepository;

@Component
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(AppUserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            String username = "admin";
            // Kolla om användaren redan finns
            if (userRepository.findByUsername(username).isEmpty()) {
                // Skapa en användare med krypterat lösenord
                AppUser user = new AppUser();
                user.setUsername(username);
                user.setPassword(passwordEncoder.encode("password"));
                user.setEmail("admin@example.com");
                user.setRole(Role.ADMIN); // Använd din enum för rollen

                // Spara användaren i databasen
                userRepository.save(user);

                System.out.println("Adminanvändare skapad med användarnamn: " + username);
            } else {
                System.out.println("Användaren " + username + " finns redan i databasen.");
            }
        };
    }
}

