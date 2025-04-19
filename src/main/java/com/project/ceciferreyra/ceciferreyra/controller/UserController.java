package com.project.ceciferreyra.ceciferreyra.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("user")
@CrossOrigin(
        origins = {"https://ceciferreyraart.vercel.app/", "http://localhost:3000"},
        allowCredentials = "true"
)
public class UserController {

    @GetMapping("/info")
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
        return principal.getAttributes();
    }


}
