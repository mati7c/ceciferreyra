package com.project.ceciferreyra.ceciferreyra.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String name, String email, String message) {
        String toEmail = "ceciliaferreyra74@gmail.com"; // CAMBIA ESTE EMAIL POR EL TUYO

        String subject = "Nuevo mensaje de " + name;
        String body = "Mensaje recibido de: " + name + "\n\n" +
                message + "\n\n" +
                "Si quieres responderle, su email es: " + email;

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false);
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(body);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("Error al enviar el correo: " + e.getMessage());
        }
    }
}

