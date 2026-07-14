package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.presentation.controller;

import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.Repository.UserRepository;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.entity.User;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.presentation.dto.request.UpdatePasswordUserRequest;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.presentation.dto.request.UpdateUserRequest;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.presentation.dto.request.UserRequestDto;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.domain.entity.UserType;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.domain.repository.UserTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;

import java.util.UUID;

import static com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.shared.util.SecurityTestUtils.asUser;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserTypeRepository userTypeRepository;

    private UserType customerType;
    private User existingUser;

    @BeforeEach
    void setUp() {
        customerType = userTypeRepository.save(UserType.create("customer"));
        existingUser = userRepository.save(
                User.create("Vinicius", "vinicius@email.com", "encoded", customerType));
    }

    @Test
    void shouldCreateUserWithValidData() throws Exception {
        UserRequestDto request = new UserRequestDto(
                "Novo Usuario", "novo@email.com", "123456", customerType.getId());

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Novo Usuario"))
                .andExpect(jsonPath("$.email").value("novo@email.com"));
    }

    @Test
    void shouldReturn400WhenEmailIsInvalid() throws Exception {
        UserRequestDto request = new UserRequestDto(
                "Novo Usuario", "email-invalido", "123456", customerType.getId());

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn409WhenEmailAlreadyInUse() throws Exception {
        UserRequestDto request = new UserRequestDto(
                "Outro Nome", "vinicius@email.com", "123456", customerType.getId());

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());
    }

    @Test
    void shouldFindUserByIdWhenAuthenticated() throws Exception {
        mockMvc.perform(get("/api/v1/users/" + existingUser.getId())
                        .with(asUser(existingUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("vinicius@email.com"));
    }

    @Test
    void shouldReturn404WhenUserIdDoesNotExist() throws Exception {
        mockMvc.perform(get("/api/v1/users/" + UUID.randomUUID())
                        .with(asUser(existingUser)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturn403WhenFindByIdWithoutAuthentication() throws Exception {
        mockMvc.perform(get("/api/v1/users/" + existingUser.getId()))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldFindUserByEmail() throws Exception {
        mockMvc.perform(get("/api/v1/users/search")
                        .with(asUser(existingUser))
                        .param("email", "vinicius@email.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Vinicius"));
    }

    @Test
    void shouldUpdateUserWhenAuthenticated() throws Exception {
        UpdateUserRequest request = new UpdateUserRequest("Vinicius Carmo", "vinicius.novo@email.com");

        mockMvc.perform(put("/api/v1/users/" + existingUser.getId())
                        .with(asUser(existingUser))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Vinicius Carmo"))
                .andExpect(jsonPath("$.email").value("vinicius.novo@email.com"));
    }

    @Test
    void shouldUpdatePasswordWhenAuthenticated() throws Exception {
        UpdatePasswordUserRequest request = new UpdatePasswordUserRequest("novaSenha123");

        mockMvc.perform(patch("/api/v1/users/" + existingUser.getId() + "/password")
                        .with(asUser(existingUser))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldDeleteUserWhenAuthenticated() throws Exception {
        mockMvc.perform(delete("/api/v1/users/" + existingUser.getId())
                        .with(asUser(existingUser)))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturn404WhenDeletingNonExistentUser() throws Exception {
        mockMvc.perform(delete("/api/v1/users/" + UUID.randomUUID())
                        .with(asUser(existingUser)))
                .andExpect(status().isNotFound());
    }
}