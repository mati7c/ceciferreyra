package com.project.ceciferreyra.ceciferreyra.config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Configuración CORS mejorada
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/piece/list").permitAll()
                        .requestMatchers(HttpMethod.GET, "/piece/list/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/exhibit/list").permitAll()
                        .requestMatchers(HttpMethod.POST, "/email/send").permitAll()
                        .requestMatchers(HttpMethod.POST, "/piece/save").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/piece/delete/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/piece/delete").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/exhibit/save").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/exhibit/delete/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .logout(logout -> logout
                        .logoutUrl("/api/logout")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            // Redirige al frontend después del logout
                            response.sendRedirect("https://ceciferreyraart.vercel.app?logout=true");
                        })
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                )
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo.oidcUserService(this.oidcUserService()))
                        .successHandler(authenticationSuccessHandler()) // Manejador personalizado
                );

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(
                "https://ceciferreyraart.vercel.app",
                "http://localhost:3030" // Para desarrollo local
        ));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        configuration.setExposedHeaders(Arrays.asList("Authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    private AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (request, response, authentication) -> {
            // 1. Generar token JWT si estás usando token-based (opcional)
            // 2. Redirigir al frontend con parámetros necesarios
            String targetUrl = "https://ceciferreyraart.vercel.app/login";

            // Puedes agregar parámetros a la URL si necesitas pasar información
            UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(targetUrl)
                    .queryParam("auth", "success");

            response.sendRedirect(builder.toUriString());
        };
    }

    private OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService() {
        final OidcUserService delegate = new OidcUserService();

        return userRequest -> {
            OidcUser oidcUser = delegate.loadUser(userRequest);
            String email = oidcUser.getEmail();
            System.out.println("Usuario autenticado: " + email);

            Set<GrantedAuthority> authorities = new HashSet<>();
            if ("matic1087@gmail.com".equals(email) || "ceciliaferreyra74@gmail.com".equals(email)) {
                authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            }

            return new DefaultOidcUser(authorities, oidcUser.getIdToken(), oidcUser.getUserInfo());
        };
    }
}

