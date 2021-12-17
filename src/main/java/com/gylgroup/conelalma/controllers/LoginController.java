package com.gylgroup.conelalma.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class LoginController {

    @GetMapping("/login")
    public ModelAndView validarLogin(@RequestParam(required = false) String error,
            @RequestParam(required = false) String logout, Principal principal) {

        ModelAndView modelAndView = new ModelAndView("login.html");

        if (error != null) {
            modelAndView.addObject("error", "EMAIL O CONTRASENIA INVÁLIDA!");
        }

        if (logout != null) {
            modelAndView.addObject("logout", "CERRRO SESIÓN EXITOSAMENTE!");
            // modelAndView.setViewName("redirect:/");
        }

        if (principal != null) {
            modelAndView.setViewName("redirect:/");
        }
        return modelAndView;
    }

}
