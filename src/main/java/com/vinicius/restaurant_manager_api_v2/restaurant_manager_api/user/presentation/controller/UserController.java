package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.presentation.controller;


import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.application.useCases.*;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.entity.User;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.presentation.dto.request.UpdatePasswordUserRequest;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.presentation.dto.request.UpdateUserRequest;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.presentation.dto.request.UserRequestDto;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.presentation.dto.response.UserResponseDto;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.presentation.mapper.UserPresentationMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final CreateUserUseCase createUserUseCase;
    private final DeleteUserByIdUseCase deleteUserByIdUseCase;
    private final FindByIdUseCase findByIdUseCase;
    private final FindUserByEmailUseCase findUserByEmailUseCase;
    private final UpdateUserPasswordUseCase updateUserPasswordUseCase;
    private final UpdateUserUseCase updateUserUseCase;

    public UserController(CreateUserUseCase createUserUseCase, DeleteUserByIdUseCase deleteUserByIdUseCase, FindByIdUseCase findByIdUseCase, FindUserByEmailUseCase findUserByEmailUseCase, UpdateUserPasswordUseCase updateUserPasswordUseCase, UpdateUserUseCase updateUserUseCase) {
        this.createUserUseCase = createUserUseCase;
        this.deleteUserByIdUseCase = deleteUserByIdUseCase;
        this.findByIdUseCase = findByIdUseCase;
        this.findUserByEmailUseCase = findUserByEmailUseCase;
        this.updateUserPasswordUseCase = updateUserPasswordUseCase;
        this.updateUserUseCase = updateUserUseCase;
    }


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

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findById(
            @PathVariable UUID id) {

        return ResponseEntity.ok(
                UserPresentationMapper.toResponse(
                        findByIdUseCase.execute(id)
                )
        );
    }

    @GetMapping("/search")
    public ResponseEntity<UserResponseDto> findByEmail(
            @RequestParam String email) {

        return ResponseEntity.ok(
                UserPresentationMapper.toResponse(
                        findUserByEmailUseCase.execute(email)
                )
        );
    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID id) {

        deleteUserByIdUseCase.execute(id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/password")
    public ResponseEntity<Void> updatePassword(
            @PathVariable UUID id,
            @Valid @RequestBody UpdatePasswordUserRequest request) {

        updateUserPasswordUseCase.execute(id, request.password());

        return ResponseEntity.noContent().build();
    }

}
