package com.project.ceciferreyra.ceciferreyra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(
                                "https://ceciferreyraart.vercel.app",  // Producción
                                "http://localhost:3030"                // Desarrollo
                        )
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .exposedHeaders(
                                "Authorization",
                                "Set-Cookie",
                                "Access-Control-Allow-Origin",
                                "Access-Control-Allow-Credentials"
                        )
                        .allowCredentials(true)
                        .maxAge(3600);  // Cache de pre-flight requests
            }
        };
    }
}
