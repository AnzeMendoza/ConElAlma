package com.gylgroup.conelalma.services;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
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

    @Async
    public void enviarEmail(String to, String nombre, String password) throws MessagingException {

        MimeMessage mimeMessage = sender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "utf-8");

        String mensajeContenido = "<head>\n" +
                " <title>Con el Alma</title>\n" +
                " </head>\n" +
                " <body style=\"background-image: linear-gradient(to right top, #051937, #004d7a, #008793, #00bf72, #a8eb12); \">\n"
                +
                " <table style=\"max-width: 600px; padding: 10px; margin:0 auto; border-collapse: collapse;\">\n" +
                " <tr>\n" +
                " <td style=\"padding: 0\">\n" +
                " <img style=\"padding: 0; display: block\"\n" +
                " src=\"https://lh3.googleusercontent.com/pw/AM-JKLWESW3119iXrZyB9Qpkqgg7OIed1SbLj4C5gM9oH_QSHbA5Fm0L11IdbIPjMkDexpVlfONaq2Ast2FXP94lCSoRDS7Wj7nkcidkpMtKfWP9_oxdVen6QaxTUAhNCjEJLThq_ha5ZaS6tQbGFRQmHFtj=w1911-h956-no?authuser=0\"\n"
                +
                " width=\"100%\">\n" +
                " </td>\n" +
                " </tr>\n" +
                " <tr>\n" +
                " <td style=\"background-color: #ecf0f1\">\n" +
                " <div style=\"color: #34495e; margin: 4% 10% 2%; text-align: justify;font-family: sans-serif\">\n" +
                " <h2 style=\"color: #e67e22; margin: 0 0 7px\">Hola " + nombre + "!</h2>\n" +
                " <p style=\"margin: 2px; font-size: 15px\">\n" +
                " Realizamos Servicios de Catering y Organización de Eventos para fiestas y eventos Empresariales,\n" +
                " Familiares y Casamientos.<br>\n" +
                " Muchas gracias por confiar en nosotros!<br>\n" +
                " Tu cuenta fue creada con exito:</p>\n" +
                " <ul style=\"font-size: 15px;  margin: 10px 0\">\n" +
                " <li>Datos de acceso:</li>\n" +
                " <li>Email: " + to + "</li>\n" +
                " <li>Contraseña: " + password + "</li>\n" +
                " </ul>\n" +
                " <div style=\"width: 100%;margin:20px 0; display: inline-block;text-align: center\">\n" +
                " <img style=\"padding: 0; width: 200px; margin: 5px\"\n" +
                " src=\"https://lh3.googleusercontent.com/pw/AM-JKLX3SjiQ2CJOktG9zWur6Zqg-UOftJJsWMuB2TVUf7UPKnVz1uk17Go65WVh3RwqIawppEwJYmbw308SKfLiYhyf0MV8xCFd9AeZd2_b3FV4Nt4hz9X1mW5ibqH27TORm03szlzDP-mk_lZxO9YYjGS7=w779-h552-no?authuser=0\">\n"
                +
                " <img style=\"padding: 0; width: 200px; margin: 5px\"\n" +
                " src=\"https://lh3.googleusercontent.com/pw/AM-JKLVXlaCdoOFVVJFO4DE8MUS7QqqMd-4lAFFaMmgfTLcYtOlSf9DzqYspg9GBku38T4UuEmyh3Y2kf2n56uxZJZQOWPe7hv0TUBi37nwQ_WepmqyoLlxl8Yaj8BT4lp70iHZj472E1uSkrJCy8JUGTmam=w735-h507-no?authuser=0\">\n"
                +
                " </div>\n" +
                " <div style=\"width: 100%; text-align: center\">\n" +
                " <a style=\"text-decoration: none; border-radius: 5px; padding: 11px 23px; color: white; background-color: #3498db\"\n"
                +
                " href=\"https://www.conelalama.com\">Ir a la página</a>\n" +
                " </div>" +
                " <p style=\"color: #b3b3b3; font-size: 12px; text-align: center;margin: 30px 0 0\">© Copyright Con El\n"
                +
                " Alma. All Rights Reserved </p>\n" +
                " </div>\n" +
                " </td>\n" +
                " </tr>\n" +
                " </table>\n" +
                " </body>\n";
        mimeMessageHelper.setText(mensajeContenido, true);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setFrom(from);
        mimeMessageHelper.setSubject(SUBJECT);
        sender.send(mimeMessage);
    }

}
