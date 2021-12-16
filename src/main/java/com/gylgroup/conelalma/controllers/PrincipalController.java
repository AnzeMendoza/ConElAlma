package com.gylgroup.conelalma.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PrincipalController {

    @GetMapping("/")
    public String iniciarHome() {
        return "admin.html";
    }

}
