package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.application.useCases;

import org.springframework.stereotype.Component;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.Repository.RestaurantRepository;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.entity.Restaurant;

import java.util.List;

@Component
public class GetAllRestaurantsUseCase {

    private final RestaurantRepository restaurantRepository;

    public GetAllRestaurantsUseCase(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public List<Restaurant> execute() {
        return restaurantRepository.findAll();
    }
}

