package com.gylgroup.conelalma.controllers;

import com.gylgroup.conelalma.entities.Cupon;
import com.gylgroup.conelalma.services.CuponService;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/cupones")
public class CuponController {

    @Autowired
    private CuponService cuponService;

    @GetMapping("/crear")
    public ModelAndView cuponCreate() {

        ModelAndView mav = new ModelAndView("cupon-formulario");
        mav.addObject("cupon", new Cupon());
        mav.addObject("title", "Crear cupon");
        //mav.addObject("action", "guardar");
        return mav;
    }

    @PostMapping("/guardar")
    public RedirectView cuponSave(Cupon cupon) {
        cuponService.save(cupon);
        return new RedirectView("/cupones/todos");
    }

    @PostMapping("/editar/{id}")
    public RedirectView cuponUpdate(@PathVariable Integer id, Cupon cupon) {
        try {
            cuponService.update(id, cupon);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new RedirectView("/cupones/todos");
    }

    @GetMapping("/todos")
    public ModelAndView cuponFindAll() {

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
    
    @GetMapping("activos")
    public ModelAndView cuponFindEnable(){
        ModelAndView mav = new ModelAndView("cuponesActivos");
        List<Cupon> cupones = null;
        try{
        cupones = cuponService.findAllAndEstado();
        } catch (Exception e){
            e.printStackTrace();
        }
        mav.addObject("cuponesActivos", cupones);
        return mav;
                
    }

}
