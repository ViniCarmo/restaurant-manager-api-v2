package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.presentation.controller;

import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.application.useCases.*;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.presentation.dto.request.UserTypeRequest;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.presentation.dto.response.UserTypeResponse;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.presentation.mapper.UserTypePresentationMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    @PostMapping
    public ResponseEntity<UserTypeResponse> createUserType(@Valid @RequestBody UserTypeRequest request) {
        var userType = createUserTypeUsecase.execute(request.name());
        return ResponseEntity.status(HttpStatus.CREATED).body(UserTypePresentationMapper.toResponse(userType));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(UUID id) {
        deleteUserTypeByIdUsecase.execute(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<UserTypeResponse> findByName(@RequestParam String name) {
        var userType = findByNameUseCase.execute(name);
        return ResponseEntity.ok(UserTypePresentationMapper.toResponse(userType));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserTypeResponse> FindById(UUID id) {
        var userType = findUserTypeByIdUseCase.execute(id);
        return ResponseEntity.ok(UserTypePresentationMapper.toResponse(userType));
    }

    @GetMapping
    public ResponseEntity<List<UserTypeResponse>> getAll() {
        var userTypes = getAllUserTypeUseCase.execute();
        var userTypeResponses = userTypes.stream()
                .map(UserTypePresentationMapper::toResponse)
                .toList();
        return ResponseEntity.ok(userTypeResponses);
    }
}
