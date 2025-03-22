package com.project.ceciferreyra.ceciferreyra.config;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("CustomOAuth2UserService: loadUser method called");
        OAuth2User oauth2User = new DefaultOAuth2UserService().loadUser(userRequest);

        // Imprime todos los atributos del usuario
        System.out.println("User attributes: " + oauth2User.getAttributes());

        // Obtener el correo del usuario autenticado
        String email = oauth2User.getAttribute("email");
        System.out.println("User email: " + email);

        // Aquí asignamos un rol ADMIN a la cuenta con un correo predeterminado
        if ("matic1087@gmail.com".equals(email)) {
            // Crear un objeto de usuario con el rol ADMIN
            List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
            return new DefaultOAuth2User(authorities, oauth2User.getAttributes(), "name");
        }

        // Si no es la cuenta predeterminada, devuelve el usuario sin asignar el rol ADMIN
        return oauth2User;
    }
}