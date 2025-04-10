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
                .cors(withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/piece/list").permitAll()
                        .requestMatchers(HttpMethod.GET, "/piece/list/**").permitAll()// ✅ Libre acceso
                        .requestMatchers(HttpMethod.GET, "/exhibit/list").permitAll()// ✅ Libre acceso
                        .requestMatchers(HttpMethod.POST, "/email/send").permitAll()// ✅ Libre acceso
                        .requestMatchers(HttpMethod.POST, "/piece/save").hasRole("ADMIN")  // 🔒 Solo ADMIN
                        .requestMatchers(HttpMethod.DELETE, "/piece/delete/**").hasRole("ADMIN")  // 🔒 Solo ADMIN
                        .requestMatchers(HttpMethod.POST, "/piece/delete").hasRole("ADMIN")  // 🔒 Solo ADMIN
                        .requestMatchers(HttpMethod.POST, "/exhibit/save").hasRole("ADMIN")  // 🔒 Solo ADMIN
                        .requestMatchers(HttpMethod.DELETE, "/exhibit/delete/**").hasRole("ADMIN")  // 🔒 Solo ADMIN
                        .anyRequest().authenticated() // 🔒 Todo lo demás requiere autenticación
                )
                .logout(logout -> logout
                        .logoutUrl("/api/logout") // ✅ Ruta de logout
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.setStatus(HttpServletResponse.SC_OK);
                        })
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                )
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo.oidcUserService(this.oidcUserService()))
                        .defaultSuccessUrl("https://ceciferryraart-frontend.vercel.app/login", true) // ✅ Redirección tras login
                );

        return http.build();
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

