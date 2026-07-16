package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.shared.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final SecurityFilter securityFilter;

    public SecurityConfig(SecurityFilter securityFilter) {
        this.securityFilter = securityFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth

                        // Login e cadastro — públicos
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/users").permitAll()
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()

                        // UserType — criação pública (necessária para o cadastro poder referenciar um tipo válido)
                        .requestMatchers(HttpMethod.POST, "/api/v1/user-types").permitAll()

                        // UserType — leitura liberada para autenticados, exclusão também (sem role específica)
                        .requestMatchers(HttpMethod.GET, "/api/v1/user-types/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/user-types/**").authenticated()

                        // Restaurantes — Customer e RestaurantOwner podem visualizar/buscar
                        .requestMatchers(HttpMethod.GET, "/api/v1/restaurants/**").hasAnyRole("CUSTOMER", "RESTAURANT_OWNER")
                        .requestMatchers(HttpMethod.GET, "/api/v1/restaurants").hasAnyRole("CUSTOMER", "RESTAURANT_OWNER")

                        // Restaurantes — apenas RestaurantOwner cria/altera/exclui
                        .requestMatchers(HttpMethod.POST, "/api/v1/restaurants").hasRole("RESTAURANT_OWNER")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/restaurants/**").hasRole("RESTAURANT_OWNER")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/restaurants/**").hasRole("RESTAURANT_OWNER")

                        // Cardápio — Customer e RestaurantOwner podem visualizar/buscar
                        .requestMatchers(HttpMethod.GET, "/api/v1/menu-items/**").hasAnyRole("CUSTOMER", "RESTAURANT_OWNER")
                        .requestMatchers(HttpMethod.GET, "/api/v1/menu-items").hasAnyRole("CUSTOMER", "RESTAURANT_OWNER")

                        // Cardápio — apenas RestaurantOwner cria/altera/exclui
                        .requestMatchers(HttpMethod.POST, "/api/v1/menu-items").hasRole("RESTAURANT_OWNER")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/menu-items/**").hasRole("RESTAURANT_OWNER")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/menu-items/**").hasRole("RESTAURANT_OWNER")

                        // Usuário — qualquer coisa além do cadastro exige login
                        .requestMatchers("/api/v1/users/**").authenticated()

                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
