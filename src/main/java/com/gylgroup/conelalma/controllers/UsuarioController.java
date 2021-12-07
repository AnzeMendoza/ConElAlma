package com.gylgroup.conelalma.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @GetMapping
    public String traerUsuarios() {
        String dato = "usuarios.html";
        return dato;
    }
}
