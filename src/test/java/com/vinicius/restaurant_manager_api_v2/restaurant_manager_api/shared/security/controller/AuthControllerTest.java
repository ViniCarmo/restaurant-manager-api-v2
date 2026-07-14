package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.shared.security.controller;

import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.shared.security.dto.request.LoginRequestDto;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.PasswordEncoderService;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.Repository.UserRepository;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.entity.User;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AuthControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserTypeRepository userTypeRepository;

    @Autowired
    private PasswordEncoderService passwordEncoderService;

    @BeforeEach
    void setUp() {
        UserType customerType = UserType.create("customer");
        userTypeRepository.save(customerType);

        User user = User.create("Vinicius", "vinicius@email.com",
                passwordEncoderService.encode("123456"), customerType);
        userRepository.save(user);
    }

    @Test
    void shouldLoginAndReturnTokenWithValidCredentials() throws Exception {
        LoginRequestDto request = new LoginRequestDto("vinicius@email.com", "123456");

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty());
    }

    @Test
    void shouldReturn401WithInvalidPassword() throws Exception {
        LoginRequestDto request = new LoginRequestDto("vinicius@email.com", "senhaErrada");

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldReturn401WithNonExistentEmail() throws Exception {
        LoginRequestDto request = new LoginRequestDto("naoexiste@email.com", "123456");

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }
}