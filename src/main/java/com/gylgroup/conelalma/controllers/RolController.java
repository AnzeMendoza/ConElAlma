package com.gylgroup.conelalma.controllers;

import com.gylgroup.conelalma.entities.Rol;
import com.gylgroup.conelalma.services.RolService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/rol")
public class RolController {

    @Autowired
    RolService rolService;

    @GetMapping("/todos")
    public ModelAndView obtenerRoles() {

        ModelAndView mav = new ModelAndView("roles-formulario");
        mav.addObject("roles", rolService.findAll());
        mav.addObject("rol", new Rol());
        mav.addObject("estado", false);
        mav.addObject("action", "guardar");

        return mav;
    }
}
