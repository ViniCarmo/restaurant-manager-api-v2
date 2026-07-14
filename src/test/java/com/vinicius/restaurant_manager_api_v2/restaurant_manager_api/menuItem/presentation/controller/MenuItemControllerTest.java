package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.presentation.controller;

import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.presentation.dto.request.MenuItemRequestDto;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.Repository.RestaurantRepository;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.entity.Restaurant;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.enums.KitchenType;
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

import java.math.BigDecimal;
import java.time.LocalTime;

import static com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.shared.util.SecurityTestUtils.asUser;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MenuItemControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserTypeRepository userTypeRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    private User restaurantOwner;
    private User customer;
    private Restaurant restaurant;

    @BeforeEach
    void setUp() {
        UserType ownerType = userTypeRepository.save(UserType.create("restaurant owner"));
        UserType customerType = userTypeRepository.save(UserType.create("customer"));

        restaurantOwner = userRepository.save(
                User.create("Vinicius", "owner@email.com", "encoded", ownerType));
        customer = userRepository.save(
                User.create("Cliente", "customer@email.com", "encoded", customerType));

        restaurant = restaurantRepository.save(Restaurant.create(
                "Pizzaria Roma", "Rua A, 123", KitchenType.ITALIAN,
                LocalTime.of(18, 0), LocalTime.of(23, 0), restaurantOwner));
    }

    @Test
    void shouldCreateMenuItemWhenLoggedUserIsRestaurantOwner() throws Exception {
        MenuItemRequestDto request = new MenuItemRequestDto(
                "Pizza Margherita", "Molho e mussarela", new BigDecimal("45.90"),
                false, "/img.jpg", restaurant.getId());

        mockMvc.perform(post("/api/v1/menu-items")
                        .with(asUser(restaurantOwner))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Pizza Margherita"));
    }

    @Test
    void shouldReturn403WhenCustomerTriesToCreateMenuItem() throws Exception {
        MenuItemRequestDto request = new MenuItemRequestDto(
                "Pizza Margherita", "Molho e mussarela", new BigDecimal("45.90"),
                false, "/img.jpg", restaurant.getId());

        mockMvc.perform(post("/api/v1/menu-items")
                        .with(asUser(customer))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldReturn403WhenAnotherOwnerTriesToCreateMenuItemOnRestaurantTheyDontOwn() throws Exception {
        UserType anotherOwnerType = userTypeRepository.save(UserType.create("restaurant owner"));
        User anotherOwner = userRepository.save(
                User.create("Outro Dono", "outro@email.com", "encoded", anotherOwnerType));

        MenuItemRequestDto request = new MenuItemRequestDto(
                "Pizza Margherita", "Molho e mussarela", new BigDecimal("45.90"),
                false, "/img.jpg", restaurant.getId());

        mockMvc.perform(post("/api/v1/menu-items")
                        .with(asUser(anotherOwner))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldAllowCustomerToViewMenuItems() throws Exception {
        mockMvc.perform(get("/api/v1/menu-items")
                        .with(asUser(customer)))
                .andExpect(status().isOk());
    }
}