package com.clinica.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    Info apiInfo() {
        return new Info().title("Clinica").description("Sistema de Turnos para odontologos")
                .termsOfService("").version("0.0.1")
                .contact(new Contact().name("Lorena Sanchez Fernandez").email("lorraines690.ls@gmail.com"));
    }

    Components components() {
        return new Components()
                .addSecuritySchemes("BearerAuth", new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .name("Authorization"));
    }

    @Bean
    public OpenAPI configureOpenApi() {
        return new OpenAPI()
                .components(components())
                .info(apiInfo())
                .addSecurityItem(new SecurityRequirement().addList("BearerAuth"));
    }

}