package com.gylgroup.conelalma.controllers;

import com.gylgroup.conelalma.services.BebidaService;
import com.gylgroup.conelalma.services.ComboService;
import com.gylgroup.conelalma.services.ComidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/Combos")
public class ComboController {
    @Autowired
    private ComboService comboService;
    @Autowired
    private BebidaService bebidaServicio;
    @Autowired
    private ComidaService comidaService;
    
    
}
