package com.gylgroup.conelalma.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PrincipalController {

    @GetMapping("/")
    public ModelAndView inicioAdmin() {
        return new ModelAndView("admin/admin");
    }

}
