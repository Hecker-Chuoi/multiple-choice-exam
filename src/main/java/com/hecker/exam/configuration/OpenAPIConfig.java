package com.hecker.exam.configuration;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI customOpenAPI(
            @Value("${springdoc.info.title}") String title,
            @Value("${springdoc.info.description}") String description,
            @Value("${springdoc.info.version}") String version
    ){
        return new OpenAPI()
                .info(new Info()
                        .title(title)
                        .description(description)
                        .version(version))
                .components(
                        new Components()
                                .addSecuritySchemes(
                                        "bearer-jwt",
                                        new SecurityScheme()
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                );
    }

    @Bean
    public GroupedOpenApi auth(){
        return GroupedOpenApi.builder()
                .group("authentication")
                .pathsToMatch("/auth/**")
                .build();
    }

    @Bean
    public GroupedOpenApi user(){
        return GroupedOpenApi.builder()
                .group("user")
                .pathsToMatch("/user/**")
                .build();
    }

    @Bean
    public GroupedOpenApi test(){
        return GroupedOpenApi.builder()
                .group("test")
                .pathsToMatch("/test/**")
                .build();
    }

    @Bean
    public GroupedOpenApi session(){
        return GroupedOpenApi.builder()
                .group("session")
                .pathsToMatch("/session/**")
                .build();
    }

    @Bean
    public GroupedOpenApi takingTest(){
        return GroupedOpenApi.builder()
                .group("taking test")
                .pathsToMatch("/taking-test/**")
                .build();
    }
}
