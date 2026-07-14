package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.presentation.controller;

import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.application.useCases.*;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.entity.Restaurant;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.domain.enums.KitchenType;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.presentation.dto.request.RestaurantRequestDto;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.presentation.dto.request.UpdateRestaurantrequestDto;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.presentation.dto.response.RestaurantResponseDto;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.presentation.mapper.RestaurantPresentationMapper;
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

@Tag(name = "Restaurantes", description = "Gerenciamento de restaurantes")
@SecurityRequirement(name = "bearer-key")
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

    @Operation(
            summary = "Criar restaurante",
            description = "Apenas usuários do tipo RESTAURANT_OWNER podem criar restaurantes"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Restaurante criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "403", description = "Usuário não é RESTAURANT_OWNER")
    })
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

    @Operation(summary = "Listar todos os restaurantes")
    @GetMapping
    public ResponseEntity<List<RestaurantResponseDto>> findAll() {

        List<RestaurantResponseDto> response =
                getAllRestaurantsUseCase.execute()
                        .stream()
                        .map(RestaurantPresentationMapper::toResponse)
                        .toList();

        return ResponseEntity.ok(response);
    }


    @Operation(summary = "Buscar restaurantes por nome e/ou tipo de cozinha")
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


    @Operation(summary = "Buscar restaurante por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Restaurante encontrado"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResponseDto> findById(
            @PathVariable UUID id) {

        return ResponseEntity.ok(
                RestaurantPresentationMapper.toResponse(
                        findRestaurantByIdUseCase.execute(id)
                )
        );
    }

    @Operation(
            summary = "Atualizar restaurante",
            description = "Apenas o dono do restaurante pode atualizá-lo"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Restaurante atualizado"),
            @ApiResponse(responseCode = "403", description = "Usuário não é o dono deste restaurante"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
    })
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

    @Operation(
            summary = "Excluir restaurante",
            description = "Apenas o dono do restaurante pode excluí-lo"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Restaurante excluído"),
            @ApiResponse(responseCode = "403", description = "Usuário não é o dono deste restaurante"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID id) {

        deleteRestaurantById.execute(id);

        return ResponseEntity.noContent().build();
    }



}
