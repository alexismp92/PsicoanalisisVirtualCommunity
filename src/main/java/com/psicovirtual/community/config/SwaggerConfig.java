package com.psicovirtual.community.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.Arrays;

@Configuration
@OpenAPIDefinition(info = @io.swagger.v3.oas.annotations.info.Info(title = "Psicoanalisis Virtual Community API", version = "1.0.0",
    description = "Psicoanalisis Virtual Community API v1.0.0 Documentation "), servers = {@Server(url = "/")})
@SecurityScheme(name = "Bearer Authentication", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "Bearer")
public class SwaggerConfig {

    /*@Bean
    public OpenAPI springOpenAPI(){
        return new OpenAPI().info(new Info().
                title("Psicoanalisis Virtual Community API").
                version("1.0.0").);
    }*/

}
