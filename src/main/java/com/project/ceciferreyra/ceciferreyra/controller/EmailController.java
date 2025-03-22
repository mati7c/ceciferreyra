package com.project.ceciferreyra.ceciferreyra.controller;

import com.project.ceciferreyra.ceciferreyra.model.EmailRequest;
import com.project.ceciferreyra.ceciferreyra.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@RequestBody EmailRequest emailRequest) {
        emailService.sendEmail(emailRequest.getName(), emailRequest.getEmail(), emailRequest.getMessage());
        return ResponseEntity.ok("Correo enviado correctamente.");
    }
}