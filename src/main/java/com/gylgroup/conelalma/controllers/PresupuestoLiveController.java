package com.gylgroup.conelalma.controllers;

import com.gylgroup.conelalma.entities.PresupuestoLive;
import com.gylgroup.conelalma.services.PresupuestoLiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/presupuesto")
public class PresupuestoLiveController {

    @Autowired
    private PresupuestoLiveService presupuestoLiveService;

    @GetMapping("/")
    public ModelAndView listar() {
        ModelAndView mav = new ModelAndView("prestamoListar");
        mav.addObject("titulo", "Listado de todos los presupuestos");
        mav.addObject("presupuestos", presupuestoLiveService.findAll());
        return mav;
    }

    @GetMapping("/formulario/{id}")
    public ModelAndView mostrarFormulario(@PathVariable("id") int id) throws Exception {
        ModelAndView mav = new ModelAndView("prestamoFormulario");
        mav.addObject("titulo", "Formulario de ingreso de prestamo");

        if(id == 0){
            mav.addObject("presupuesto", new PresupuestoLive());
        } else {
            mav.addObject("presupuesto", presupuestoLiveService.findById(id));
        }

        return mav;
    }

/*    @GetMapping("/{id}")
    public ModelAndView listarUno(@PathVariable("id") int id) throws Exception {
        ModelAndView mav = new ModelAndView("prestamoListar");
        mav.addObject("prestamos", presupuestoLiveService.findById(id));
        return mav;
    }*/
}



