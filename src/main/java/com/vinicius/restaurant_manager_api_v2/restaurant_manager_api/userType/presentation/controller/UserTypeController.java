package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.presentation.controller;

import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.application.useCases.*;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.presentation.dto.request.UserTypeRequest;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.presentation.dto.response.UserTypeResponse;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.presentation.mapper.UserTypePresentationMapper;
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

@Tag(name = "Tipos de Usuário", description = "CRUD de tipos de usuário (Customer / Restaurant Owner)")
@SecurityRequirement(name = "bearer-key")
@RestController
@RequestMapping("/api/v1/user-types")
public class UserTypeController {

    private final CreateUserTypeUsecase createUserTypeUsecase;
    private final DeleteUserTypeByIdUsecase deleteUserTypeByIdUsecase;
    private final FindByNameUseCase findByNameUseCase;
    private final FindUserTypeByIdUseCase findUserTypeByIdUseCase;
    private final GetAllUserTypeUseCase getAllUserTypeUseCase;

    public UserTypeController(CreateUserTypeUsecase createUserTypeUsecase, DeleteUserTypeByIdUsecase deleteUserTypeByIdUsecase, FindByNameUseCase findByNameUseCase, FindUserTypeByIdUseCase findUserTypeByIdUseCase, GetAllUserTypeUseCase getAllUserTypeUseCase) {
        this.createUserTypeUsecase = createUserTypeUsecase;
        this.deleteUserTypeByIdUsecase = deleteUserTypeByIdUsecase;
        this.findByNameUseCase = findByNameUseCase;
        this.findUserTypeByIdUseCase = findUserTypeByIdUseCase;
        this.getAllUserTypeUseCase = getAllUserTypeUseCase;
    }

    @Operation(summary = "Criar tipo de usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Tipo de usuário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "409", description = "Tipo de usuário já existe")
    })
    @PostMapping
    public ResponseEntity<UserTypeResponse> createUserType(@Valid @RequestBody UserTypeRequest request) {
        var userType = createUserTypeUsecase.execute(request.name());
        return ResponseEntity.status(HttpStatus.CREATED).body(UserTypePresentationMapper.toResponse(userType));
    }

    @Operation(summary = "Deletar tipo de usuário por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Tipo de usuário deletado"),
            @ApiResponse(responseCode = "404", description = "Tipo de usuário não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        deleteUserTypeByIdUsecase.execute(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Buscar tipo de usuário por nome")
    @GetMapping("/search")
    public ResponseEntity<UserTypeResponse> findByName(@RequestParam String name) {
        var userType = findByNameUseCase.execute(name);
        return ResponseEntity.ok(UserTypePresentationMapper.toResponse(userType));
    }

    @Operation(summary = "Buscar tipo de usuário por ID")
    @GetMapping("/{id}")
    public ResponseEntity<UserTypeResponse> FindById(@PathVariable UUID id) {
        var userType = findUserTypeByIdUseCase.execute(id);
        return ResponseEntity.ok(UserTypePresentationMapper.toResponse(userType));
    }

    @Operation(summary = "Listar todos os tipos de usuário")
    @GetMapping
    public ResponseEntity<List<UserTypeResponse>> getAll() {
        var userTypes = getAllUserTypeUseCase.execute();
        var userTypeResponses = userTypes.stream()
                .map(UserTypePresentationMapper::toResponse)
                .toList();
        return ResponseEntity.ok(userTypeResponses);
    }
}
