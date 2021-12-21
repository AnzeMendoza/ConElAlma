package com.gylgroup.conelalma.controllers;

import com.gylgroup.conelalma.entities.Usuario;
import com.gylgroup.conelalma.services.ComentarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class PrincipalController {

    @Autowired
    private ComentarioService comentarioService;

    @GetMapping("/")
    public ModelAndView inicioPublic(HttpSession session, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("index");
        Usuario user = (Usuario) session.getAttribute("user");
        Map<String,?> map = RequestContextUtils.getInputFlashMap(request);
        if(session.getAttribute("user")!=null){
            //Agregar if para redirigira para panel admin o public
            if(user.getRol().getId()==1){
                mav.addObject("logueado","true");
                mav.addObject("usuario",user);
                if (map!=null){
                    mav.addObject("exito",map.get("exito"));
                }

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
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ModelAndView inicioAdmin() {
        return new ModelAndView("admin/admin");
    }

}
