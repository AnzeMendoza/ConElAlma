package com.gylgroup.conelalma.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender sender;

    @Value("${spring.mail.username}")
    private String from;

    private static final String SUBJECT = "Correo de Bienvenida!";
    private static final String TEXT = "Bienvenido a Con el Alma eventos y caterings. Gracias por registrarse!";

    public void enviarThread(String to, String password) {
        new Thread(() -> {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setFrom(from);
            message.setSubject(SUBJECT);
            message.setText(TEXT + " Sus datos de Acceso son email: " + to + " y password: " + password);
            sender.send(message);
        }).start();
    }

}
