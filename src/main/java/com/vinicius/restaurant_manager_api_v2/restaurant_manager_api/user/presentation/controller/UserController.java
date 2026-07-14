package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.presentation.controller;


import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.application.useCases.*;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.entity.User;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.presentation.dto.request.UpdatePasswordUserRequest;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.presentation.dto.request.UpdateUserRequest;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.presentation.dto.request.UserRequestDto;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.presentation.dto.response.UserResponseDto;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.presentation.mapper.UserPresentationMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Usuários", description = "Gerenciamento de usuários do sistema")
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final CreateUserUseCase createUserUseCase;
    private final DeleteUserByIdUseCase deleteUserByIdUseCase;
    private final FindUserByIdUseCase findUserByIdUseCase;
    private final SearchUserByEmailUseCase searchUserByEmailUseCase;
    private final UpdateUserPasswordUseCase updateUserPasswordUseCase;
    private final UpdateUserUseCase updateUserUseCase;

    public UserController(CreateUserUseCase createUserUseCase, DeleteUserByIdUseCase deleteUserByIdUseCase, FindUserByIdUseCase findUserByIdUseCase, SearchUserByEmailUseCase searchUserByEmailUseCase, UpdateUserPasswordUseCase updateUserPasswordUseCase, UpdateUserUseCase updateUserUseCase) {
        this.createUserUseCase = createUserUseCase;
        this.deleteUserByIdUseCase = deleteUserByIdUseCase;
        this.findUserByIdUseCase = findUserByIdUseCase;
        this.searchUserByEmailUseCase = searchUserByEmailUseCase;
        this.updateUserPasswordUseCase = updateUserPasswordUseCase;
        this.updateUserUseCase = updateUserUseCase;
    }

    @Operation(summary = "Cadastrar novo usuário", description = "Endpoint público de cadastro")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "409", description = "Email já cadastrado")
    })
    @PostMapping
    public ResponseEntity<UserResponseDto> create(
            @Valid @RequestBody UserRequestDto request) {

        User user = createUserUseCase.execute(
                request.name(),
                request.email(),
                request.password(),
                request.userTypeId()
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(UserPresentationMapper.toResponse(user));
    }

    @Operation(summary = "Buscar usuário por ID")
    @SecurityRequirement(name = "bearer-key")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findById(
            @PathVariable UUID id) {

        return ResponseEntity.ok(
                UserPresentationMapper.toResponse(
                        findUserByIdUseCase.execute(id)
                )
        );
    }

    @Operation(summary = "Buscar usuário por email")
    @SecurityRequirement(name = "bearer-key")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping("/search")
    public ResponseEntity<UserResponseDto> findByEmail(
            @RequestParam String email) {

        return ResponseEntity.ok(
                UserPresentationMapper.toResponse(
                        searchUserByEmailUseCase.execute(email)
                )
        );
    }

    @Operation(summary = "Atualizar usuário")
    @SecurityRequirement(name = "bearer-key")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "409", description = "Email já cadastrado por outro usuário")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateUserRequest request) {

        User user = updateUserUseCase.execute(
                id,
                request.name(),
                request.email()
        );

        return ResponseEntity.ok(
                UserPresentationMapper.toResponse(user)
        );
    }

    @Operation(summary = "Excluir usuário")
    @SecurityRequirement(name = "bearer-key")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Usuário excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID id) {

        deleteUserByIdUseCase.execute(id);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Atualizar senha do usuário")
    @SecurityRequirement(name = "bearer-key")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Senha atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Senha inválida"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @PatchMapping("/{id}/password")
    public ResponseEntity<Void> updatePassword(
            @PathVariable UUID id,
            @Valid @RequestBody UpdatePasswordUserRequest request) {

        updateUserPasswordUseCase.execute(id, request.password());

        return ResponseEntity.noContent().build();
    }

}
