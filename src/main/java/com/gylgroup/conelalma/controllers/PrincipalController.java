package com.gylgroup.conelalma.controllers;

import com.gylgroup.conelalma.services.ComentarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PrincipalController {

    @Autowired
    private ComentarioService comentarioService;

    @GetMapping("/")
    public ModelAndView inicioPublic() {
        ModelAndView mav = new ModelAndView("public/index.html");
        mav.addObject("comentarios",comentarioService.traerAltas());
        return mav;
    }

    @GetMapping("/admin")
    public ModelAndView inicioHome() {
        return new ModelAndView("admin");
    }

}
