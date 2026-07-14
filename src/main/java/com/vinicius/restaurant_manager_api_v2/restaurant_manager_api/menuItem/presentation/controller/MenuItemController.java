package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.presentation.controller;

import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.application.useCases.*;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.domain.entity.MenuItem;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.presentation.dto.request.MenuItemRequestDto;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.presentation.dto.request.UpdateMenuItemrequestDto;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.presentation.dto.response.MenuItemResponseDto;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.presentation.mapper.MenuItemPresentationMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Itens do Cardápio", description = "Gerenciamento dos itens de cardápio dos restaurantes")
@SecurityRequirement(name = "bearer-key")
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

    @Operation(
            summary = "Criar item do cardápio",
            description = "Apenas o dono do restaurante pode criar itens no cardápio"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Item criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "403", description = "Usuário não é o dono do restaurante"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
    })
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

    @Operation(summary = "Listar todos os itens de cardápio")
    @GetMapping
    public ResponseEntity<List<MenuItemResponseDto>> findAll() {

        List<MenuItemResponseDto> response = getAllMenuItemUseCase.execute()
                .stream()
                .map(MenuItemPresentationMapper::toResponse)
                .toList();

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Buscar item do cardápio por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Item encontrado"),
            @ApiResponse(responseCode = "404", description = "Item não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<MenuItemResponseDto> findById(
            @PathVariable UUID id) {

        return ResponseEntity.ok(
                MenuItemPresentationMapper.toResponse(
                        findMenuItemByIdUseCase.execute(id)
                )
        );
    }

    @Operation(summary = "Listar itens do cardápio de um restaurante")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
    })
    @GetMapping("/{restaurantId}/menu-items")
    public ResponseEntity<List<MenuItemResponseDto>> findByRestaurant(
            @PathVariable UUID restaurantId) {

        List<MenuItemResponseDto> response = listMenuItemsByRestaurantUseCase.execute(restaurantId)
                .stream()
                .map(MenuItemPresentationMapper::toResponse)
                .toList();

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Atualizar item do cardápio",
            description = "Apenas o dono do restaurante pode atualizar itens do cardápio"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Item atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "403", description = "Usuário não é o dono do restaurante"),
            @ApiResponse(responseCode = "404", description = "Item não encontrado")
    })
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

    @Operation(
            summary = "Excluir item do cardápio",
            description = "Apenas o dono do restaurante pode excluir itens do cardápio"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Item excluído com sucesso"),
            @ApiResponse(responseCode = "403", description = "Usuário não é o dono do restaurante"),
            @ApiResponse(responseCode = "404", description = "Item não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID id) {

        deleteMenuByIdUseCase.execute(id);

        return ResponseEntity.noContent().build();
    }
}

