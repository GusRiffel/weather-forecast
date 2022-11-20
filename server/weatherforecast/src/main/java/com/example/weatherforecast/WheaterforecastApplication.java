package com.example.weatherforecast;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.service.ApiKey;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
public class WheaterforecastApplication {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    private ApiKey apiKey() {
        return new ApiKey("JWT", "Authorization", "header");
    }

    public static void main(String[] args) {
        SpringApplication.run(WheaterforecastApplication.class, args);
    }
}
