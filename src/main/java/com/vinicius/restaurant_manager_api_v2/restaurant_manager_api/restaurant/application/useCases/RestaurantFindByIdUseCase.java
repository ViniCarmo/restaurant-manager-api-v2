package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.application.useCases;

import org.springframework.stereotype.Component;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.Repository.RestaurantRepository;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.entity.Restaurant;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.exception.RestaurantNotFoundException;

import java.util.UUID;

@Component
public class RestaurantFindByIdUseCase {
  private final RestaurantRepository restaurantRepository;

    public RestaurantFindByIdUseCase(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public Restaurant execute(UUID id){
      return restaurantRepository.findById(id).orElseThrow(()
              -> new RestaurantNotFoundException("Restaurant not found with id: " + id));
  }

}
