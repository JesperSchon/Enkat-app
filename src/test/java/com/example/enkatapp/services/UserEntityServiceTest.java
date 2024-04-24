package com.example.enkatapp.services;

import com.example.enkatapp.models.UserEntity;
import com.example.enkatapp.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserEntityServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserEntityService userEntityService;

    private UserEntity user;

    private UserEntity updatedUserDetails;

    @BeforeEach
    void setUp() {
        user = new UserEntity();
        user.setUsername("testUser");
        user.setPassword("password");
        user.setEmail("test@example.com");

        updatedUserDetails = new UserEntity();
        updatedUserDetails.setUsername("updatedUser");
        updatedUserDetails.setPassword("newPassword");
        updatedUserDetails.setEmail("updated@example.com");
    }

    @Test
    void createUser() {
        // Ställ in förväntat beteende
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encryptedPassword");
        when(userRepository.save(any(UserEntity.class))).thenReturn(user);

        // Utför testet
        UserEntity savedUser = userEntityService.createUser(user);

        // Verifiera att lösenordet krypteras
        assertEquals("encryptedPassword", savedUser.getPassword());

        // Verifiera att save-metoden anropades
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void updateUser() {
        Long userId = 1L;

        // Ställ in förväntat beteende
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(any(UserEntity.class))).thenReturn(updatedUserDetails);

        // Utför testet
        UserEntity updatedUser = userEntityService.updateUser(userId, updatedUserDetails);

        // Verifiera att de rätta metoderna anropades
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(any(UserEntity.class));

        // Verifiera att användaruppgifterna uppdaterades korrekt
        assertEquals(updatedUserDetails.getUsername(), updatedUser.getUsername());
        assertEquals(updatedUserDetails.getEmail(), updatedUser.getEmail());
    }

    @Test
    void deleteUser() {
        Long userId = 1L;

        // Utför testet
        userEntityService.deleteUser(userId);

        // Verifiera att deleteById-metoden anropades med rätt ID
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void findById() {
        Long userId = 1L;

        // Ställ in förväntat beteende för userRepository
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Utför testet
        UserEntity foundUser = userEntityService.findById(userId);

        // Verifiera att findById-metoden anropades på userRepository
        verify(userRepository, times(1)).findById(userId);

        // Verifiera att returnerad användare är den förväntade
        assertNotNull(foundUser);
        assertEquals(user.getId(), foundUser.getId());
        assertEquals(user.getUsername(), foundUser.getUsername());
        assertEquals(user.getEmail(), foundUser.getEmail());
    }

}
