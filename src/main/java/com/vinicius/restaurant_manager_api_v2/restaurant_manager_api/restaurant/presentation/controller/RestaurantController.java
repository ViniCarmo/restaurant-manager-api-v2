package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.presentation.controller;

import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.application.useCases.*;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.entity.Restaurant;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.enums.KitchenType;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.presentation.dto.request.RestaurantRequestDto;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.presentation.dto.request.UpdateRestaurantrequestDto;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.presentation.dto.response.RestaurantResponseDto;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.presentation.mapper.RestaurantPresentationMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/restaurants")
public class RestaurantController {

    private final CreateRestaurantUseCase createRestaurantUseCase;
    private final DeleteRestaurantById deleteRestaurantById;
    private final GetAllRestaurantsUseCase getAllRestaurantsUseCase;
    private final FindRestaurantByIdUseCase findRestaurantByIdUseCase;
    private final SearchRestaurantUseCase searchRestaurantUseCase;
    private final UpdateRestaurantUseCase updateRestaurantUseCase;

    public RestaurantController(CreateRestaurantUseCase createRestaurantUseCase, DeleteRestaurantById deleteRestaurantById, GetAllRestaurantsUseCase getAllRestaurantsUseCase, FindRestaurantByIdUseCase findRestaurantByIdUseCase, SearchRestaurantUseCase searchRestaurantUseCase, UpdateRestaurantUseCase updateRestaurantUseCase) {
        this.createRestaurantUseCase = createRestaurantUseCase;
        this.deleteRestaurantById = deleteRestaurantById;
        this.getAllRestaurantsUseCase = getAllRestaurantsUseCase;
        this.findRestaurantByIdUseCase = findRestaurantByIdUseCase;
        this.searchRestaurantUseCase = searchRestaurantUseCase;
        this.updateRestaurantUseCase = updateRestaurantUseCase;
    }


    @PostMapping
    public ResponseEntity<RestaurantResponseDto> create(
            @Valid @RequestBody RestaurantRequestDto request) {

        Restaurant restaurant = createRestaurantUseCase.execute(
                request.name(),
                request.address(),
                request.kitchenType(),
                request.openingTime(),
                request.closingTime(),
                request.restaurantOwnerId()
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(RestaurantPresentationMapper.toResponse(restaurant));
    }

    @GetMapping
    public ResponseEntity<List<RestaurantResponseDto>> findAll() {

        List<RestaurantResponseDto> response =
                getAllRestaurantsUseCase.execute()
                        .stream()
                        .map(RestaurantPresentationMapper::toResponse)
                        .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<List<RestaurantResponseDto>> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) KitchenType kitchenType) {

        List<RestaurantResponseDto> response =
                searchRestaurantUseCase.execute(name, kitchenType)
                        .stream()
                        .map(RestaurantPresentationMapper::toResponse)
                        .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResponseDto> findById(
            @PathVariable UUID id) {

        return ResponseEntity.ok(
                RestaurantPresentationMapper.toResponse(
                        findRestaurantByIdUseCase.execute(id)
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestaurantResponseDto> update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateRestaurantrequestDto request) {

        Restaurant restaurant = updateRestaurantUseCase.execute(
                id,
                request.name(),
                request.address(),
                request.openingTime(),
                request.closingTime()
        );

        return ResponseEntity.ok(
                RestaurantPresentationMapper.toResponse(restaurant)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID id) {

        deleteRestaurantById.execute(id);

        return ResponseEntity.noContent().build();
    }



}
