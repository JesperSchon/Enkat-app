package com.example.enkatapp.controllers;

import com.example.enkatapp.models.Role;
import com.example.enkatapp.models.User;
import com.example.enkatapp.repositories.UserRepository;
import com.example.enkatapp.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
    }


}
