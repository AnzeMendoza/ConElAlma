package com.gylgroup.conelalma.controllers;

import com.gylgroup.conelalma.entities.PresupuestoLive;
import com.gylgroup.conelalma.enums.TipoEvento;
import com.gylgroup.conelalma.services.PresupuestoLiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.EnumSet;

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
        mav.addObject("presupuestosActivos", presupuestoLiveService.findByEstadoTrue());
        return mav;
    }

    @GetMapping("/formulario/{id}")
    public ModelAndView mostrarFormulario(@PathVariable("id") int id) throws Exception {
        ModelAndView mav = new ModelAndView("prestamoFormulario");
        mav.addObject("titulo", "Formulario de ingreso de prestamo");
        mav.addObject("tiposDeEventos", new ArrayList<>(EnumSet.allOf(TipoEvento.class)));

        if (id == 0 ) {
            mav.addObject("presupuesto", new PresupuestoLive());
        } else {
            mav.addObject("presupuesto", presupuestoLiveService.findById(id));
        }
        return mav;
    }

    @PostMapping("/formulario/{id}")
    public RedirectView cargarFormulario(@PathVariable("id") int id,
                                   @Valid @ModelAttribute("prespuesto") PresupuestoLive presupuestoLive,
                                   BindingResult result,
                                   RedirectAttributes redirectAttributes) throws Exception {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(presupuestoLive);
            return new RedirectView("/presupuesto/formulario/"+0);
        }

        if (id == 0) {
            presupuestoLiveService.save(presupuestoLive);
        } else {
            presupuestoLiveService.update(presupuestoLive, id);
        }
        return new RedirectView("/presupuesto/");
    }

    /*@GetMapping("/{id}")
    public ModelAndView listarUno(@PathVariable("id") int id) throws Exception {
        ModelAndView mav = new ModelAndView("prestamoListar");
        mav.addObject("prestamos", presupuestoLiveService.findById(id));
        return mav;
    }*/

    @GetMapping("/activar/{id}")
    public RedirectView activar(@PathVariable("id") int id) {
        try {
            presupuestoLiveService.enable(id);
        } catch (Exception e) {

        }
        return new RedirectView("/presupuesto/");
    }

    @GetMapping("/desactivar/{id}")
    public RedirectView desactivar(@PathVariable("id") int id) {
        try {
            presupuestoLiveService.disable(id);
        } catch (Exception e) {

        }
        return new RedirectView("/presupuesto/");
    }
}




