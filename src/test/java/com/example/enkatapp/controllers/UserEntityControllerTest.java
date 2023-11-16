package com.example.enkatapp.controllers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.enkatapp.models.UserEntity;
import com.example.enkatapp.services.UserEntityService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = UserEntityController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(SpringExtension.class)
public class UserEntityControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserEntityService userEntityService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void UserEntityController_CreateUser_ReturnCreatedUser() throws Exception {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("test");
        userEntity.setPassword("test");
        userEntity.setEmail("test@test.com");

        given(userEntityService.createUser(any(UserEntity.class))).willReturn(userEntity);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userEntity)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value(userEntity.getUsername()))
                .andExpect(jsonPath("$.password").value(userEntity.getPassword()))
                .andExpect(jsonPath("$.email").value(userEntity.getEmail()))
                .andDo(print());
    }

    @Test
    public void deleteUserTest() throws Exception {
        // Ställ in mock-beteendet
        willDoNothing().given(userEntityService).deleteUser(anyLong());

        // Användarens ID som ska raderas
        Long userId = 1L;

        // Utför DELETE-förfrågan
        mockMvc.perform(delete("/api/users/{id}", userId))
                .andExpect(status().isNoContent()) // Förväntar sig status 204 No Content
                .andDo(print());
    }

    @Test
    public void updateUserTest() throws Exception {
        // Skapa en användare som ska uppdateras
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("test");
        userEntity.setPassword("test");
        userEntity.setEmail("test@test.se");

        // Skapa en uppdaterad användare
        UserEntity updatedUserEntity = new UserEntity();
        updatedUserEntity.setUsername("test2");
        updatedUserEntity.setPassword("test2");
        updatedUserEntity.setEmail("test2@test.se");

        // Ställ in mock-beteendet
        given(userEntityService.updateUser(anyLong(), any(UserEntity.class))).willReturn(updatedUserEntity);

        // Utför PUT-förfrågan
        mockMvc.perform(put("/api/users/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userEntity)))
                .andExpect(status().isOk()) // Förväntar sig status 200 OK
                .andExpect(jsonPath("$.username").value(updatedUserEntity.getUsername()))
                .andExpect(jsonPath("$.password").value(updatedUserEntity.getPassword()))
                .andExpect(jsonPath("$.email").value(updatedUserEntity.getEmail()))
                .andDo(print());
    }
}