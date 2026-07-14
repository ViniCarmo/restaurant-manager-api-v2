package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.presentation.controller;

import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.presentation.dto.request.UserTypeRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserTypeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturn403WhenNotAuthenticated() throws Exception {
        mockMvc.perform(get("/api/v1/user-types"))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldCreateUserTypeWhenAuthenticated() throws Exception {
        UserTypeRequest request = new UserTypeRequest("CUSTOMER");

        mockMvc.perform(post("/api/v1/user-types")
                        .with(user("test@email.com").roles("CUSTOMER"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("CUSTOMER"));
    }

    @Test
    void shouldReturn400WhenNameIsBlank() throws Exception {
        UserTypeRequest request = new UserTypeRequest("");

        mockMvc.perform(post("/api/v1/user-types")
                        .with(user("test@email.com").roles("CUSTOMER"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnEmptyListWhenNoUserTypesExist() throws Exception {
        mockMvc.perform(get("/api/v1/user-types")
                        .with(user("test@email.com").roles("CUSTOMER")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }
}