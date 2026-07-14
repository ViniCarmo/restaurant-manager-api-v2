package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.shared.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI restaurantManagerOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")))

                .addSecurityItem(
                        new SecurityRequirement()
                                .addList("bearer-key"))
                .info(new Info()
                        .title("Restaurant Manager API")
                        .description("""
                                API desenvolvida para gerenciamento de restaurantes.

                                Funcionalidades:
                                - Cadastro de usuários
                                - Cadastro de tipos de usuário
                                - Gerenciamento de restaurantes
                                - Gerenciamento de cardápios
                                - Autenticação JWT
                                - Controle de acesso por Roles
                                """)
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Vinicius Carmo")
                                .email("seuemail@email.com")
                                .url("https://github.com/ViniCarmo"))
                        .license(new License()
                                .name("MIT")))
                .externalDocs(new ExternalDocumentation()
                        .description("GitHub")
                        .url("https://github.com/ViniCarmo/restaurant-manager-api-v2"));
    }



}