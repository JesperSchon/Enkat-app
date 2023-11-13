package com.example.enkatapp.security;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.example.enkatapp.models.UserEntity;
import com.example.enkatapp.repositories.UserRepository;

@Component
public class DataInitializer {

   /* @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            String username = "admin";
            // Kolla om användaren redan finns
            if (userRepository.findByUsername(username).isEmpty()) {
                // Skapa en användare med krypterat lösenord
                UserEntity user = new UserEntity();
                user.setUsername(username);
                user.setPassword(passwordEncoder.encode("password"));
                user.setEmail("admin@example.com");
                //user.setRole();

                // Spara användaren i databasen
                userRepository.save(user);

                System.out.println("Adminanvändare skapad med användarnamn: " + username);
            } else {
                System.out.println("Användaren " + username + " finns redan i databasen.");
            }
        };
    }*/
}

