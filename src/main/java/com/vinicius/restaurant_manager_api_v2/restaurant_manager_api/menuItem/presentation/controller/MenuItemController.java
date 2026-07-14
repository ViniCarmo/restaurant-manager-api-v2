package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.presentation.controller;

import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.application.useCases.*;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.domain.entity.MenuItem;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.presentation.dto.request.MenuItemRequestDto;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.presentation.dto.request.UpdateMenuItemrequestDto;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.presentation.dto.response.MenuItemResponseDto;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.presentation.mapper.MenuItemPresentationMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/menu-items")
public class MenuItemController {

    private final CreateMenuUseCase createMenuUseCase;
    private final DeleteMenuByIdUseCase deleteMenuByIdUseCase;
    private final FindMenuItemByIdUseCase findMenuItemByIdUseCase;
    private final ListMenuItemsByRestaurantUseCase listMenuItemsByRestaurantUseCase;
    private final GetAllMenuItemUseCase getAllMenuItemUseCase;
    private final UpdateMenuItemUseCase updateMenuItemUseCase;

    public MenuItemController(CreateMenuUseCase createMenuUseCase, DeleteMenuByIdUseCase deleteMenuByIdUseCase, FindMenuItemByIdUseCase findMenuItemByIdUseCase, ListMenuItemsByRestaurantUseCase listMenuItemsByRestaurantUseCase, GetAllMenuItemUseCase getAllMenuItemUseCase, UpdateMenuItemUseCase updateMenuItemUseCase) {
        this.createMenuUseCase = createMenuUseCase;
        this.deleteMenuByIdUseCase = deleteMenuByIdUseCase;
        this.findMenuItemByIdUseCase = findMenuItemByIdUseCase;
        this.listMenuItemsByRestaurantUseCase = listMenuItemsByRestaurantUseCase;
        this.getAllMenuItemUseCase = getAllMenuItemUseCase;
        this.updateMenuItemUseCase = updateMenuItemUseCase;
    }

    @PostMapping
    public ResponseEntity<MenuItemResponseDto> createMenuItem(@Valid @RequestBody MenuItemRequestDto request) {
        MenuItem menuItem = createMenuUseCase.execute(
                request.name(),
                request.description(),
                request.price(),
                request.dineInOnly(),
                request.photoPath(),
                request.restaurantId()
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(MenuItemPresentationMapper.toResponse(menuItem));
    }

    @GetMapping
    public ResponseEntity<List<MenuItemResponseDto>> findAll() {

        List<MenuItemResponseDto> response = getAllMenuItemUseCase.execute()
                .stream()
                .map(MenuItemPresentationMapper::toResponse)
                .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuItemResponseDto> findById(
            @PathVariable UUID id) {

        return ResponseEntity.ok(
                MenuItemPresentationMapper.toResponse(
                        findMenuItemByIdUseCase.execute(id)
                )
        );
    }

    @GetMapping("/{restaurantId}/menu-items")
    public ResponseEntity<List<MenuItemResponseDto>> findByRestaurant(
            @PathVariable UUID restaurantId) {

        List<MenuItemResponseDto> response = listMenuItemsByRestaurantUseCase.execute(restaurantId)
                .stream()
                .map(MenuItemPresentationMapper::toResponse)
                .toList();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MenuItemResponseDto> update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateMenuItemrequestDto request) {

        MenuItem menuItem = updateMenuItemUseCase.execute(
                id,
                request.name(),
                request.description(),
                request.price(),
                request.dineInOnly(),
                request.photoPath()
        );

        return ResponseEntity.ok(
                MenuItemPresentationMapper.toResponse(menuItem)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID id) {

        deleteMenuByIdUseCase.execute(id);

        return ResponseEntity.noContent().build();
    }
}

