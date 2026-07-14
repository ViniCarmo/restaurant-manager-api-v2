package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.shared.security.controller;

import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.shared.security.TokenService;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.shared.security.dto.request.LoginRequestDto;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.shared.security.dto.response.LoginResponseDto;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.infrastructure.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Autenticação", description = "Endpoint de login")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {


    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @Operation(summary = "Login", description = "Autentica o usuário e retorna um token JWT")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Email ou senha inválidos")
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto request) {

        var usernamePassword = new UsernamePasswordAuthenticationToken(
                request.email(), request.password());

        Authentication authentication = authenticationManager.authenticate(usernamePassword);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String token = tokenService.generateToken(userDetails);

        return ResponseEntity.ok(new LoginResponseDto(token));
    }
}
