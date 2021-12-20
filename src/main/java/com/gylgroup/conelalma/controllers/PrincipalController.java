package com.gylgroup.conelalma.controllers;

import com.gylgroup.conelalma.entities.Usuario;
import com.gylgroup.conelalma.services.ComentarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class PrincipalController {

    @Autowired
    private ComentarioService comentarioService;

    @GetMapping("/")
    public ModelAndView inicioPublic(HttpSession session) {
        ModelAndView mav = new ModelAndView("index");
        Usuario user = (Usuario) session.getAttribute("user");

        if(session.getAttribute("user")!=null){
            //Agregar if para redirigira para panel admin o public
            if(user.getRol().getId()==1){
                mav.addObject("logueado","true");
                mav.addObject("usuario",user);
                mav.addObject("comentarios",comentarioService.findAllEnable());
            }else{
                mav.setViewName("redirect:/admin");
                mav.addObject("logueado","true");
                mav.addObject("usuario",user);
                mav.addObject("comentarios",comentarioService.findAllEnable());
            }
        }else{
            mav.addObject("logueado","false");//false por defecto
            mav.addObject("comentarios",comentarioService.findAllEnable());
           // mav.setViewName("redirect:/");
        }

        return mav;
    }

    @GetMapping("/admin")
    public ModelAndView inicioAdmin() {
        return new ModelAndView("admin/admin");
    }

}
