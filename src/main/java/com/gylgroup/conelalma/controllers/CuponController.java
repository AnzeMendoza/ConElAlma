
package com.gylgroup.conelalma.controllers;

import com.gylgroup.conelalma.entities.Cupon;
import com.gylgroup.conelalma.services.CuponService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;



@Controller
@RequestMapping("/cupones")
public class CuponController {

/*
    @Autowired
    private CuponService cuponService;
    
    @GetMapping("/todos")
    public ModelAndView cuponFindAll(){
        ModelAndView mav = new ModelAndView("cupones");
        List<Cupon> cupones = null;
        try {
            cupones = cuponService.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mav.addObject("cupones", cupones);
        return mav;
    }
    
    
    @PostMapping("/guardar")
    public RedirectView save(@RequestParam String nombre, @RequestParam String codigo, @RequestParam Integer descuento){
    cuponService.save(codigo, descuento);
    return new RedirectView("/todos");
    }*/
}
