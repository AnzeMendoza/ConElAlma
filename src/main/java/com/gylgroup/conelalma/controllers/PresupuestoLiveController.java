package com.gylgroup.conelalma.controllers;

import com.gylgroup.conelalma.entities.PresupuestoLive;
import com.gylgroup.conelalma.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;

@Controller
@RequestMapping("/presupuesto")
public class PresupuestoLiveController {

    @Autowired
    private PresupuestoLiveService presupuestoLiveService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private LocalService localService;

    @Autowired
    private CuponService cuponService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/")
    public ModelAndView listar() {
        ModelAndView mav = new ModelAndView("presupuestoListar");
        mav.addObject("titulo", "Listado de todos los presupuestos");
        mav.addObject("presupuestos", presupuestoLiveService.findAll());
        mav.addObject("presupuestosActivos", presupuestoLiveService.findByEstadoTrue());
        return mav;
    }

    @GetMapping("/agregar/{id}")
    public String prestamoFormulario(Model model, @PathVariable("id") int id) throws Exception {
        sendDataEntities(model);
        if (id == 0) {
            model.addAttribute("presupuesto", new PresupuestoLive());
        } else {
            model.addAttribute("presupuesto", presupuestoLiveService.findById(id));
        }
        return "presupuestoFormulario";
    }

    @PostMapping("/agregar/{id}")
    public String prestamoAlta(Model model,
                               @Valid @ModelAttribute("presupuesto") PresupuestoLive presupuesto,
                               BindingResult result,
                               @PathVariable("id") int id) throws Exception {
        if (result.hasErrors()) {
            sendDataEntities(model);
            return "presupuestoFormulario";
        }

        if (id == 0) {
            presupuestoLiveService.save(presupuesto);
        } else {
            presupuestoLiveService.update(presupuesto, id);
        }
        return "redirect:/presupuesto/";
    }

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

    private void sendDataEntities(Model model) {
        model.addAttribute("titulo", "Formulario de presupuesto");
//        model.addAttribute("tiposDeEventos", new ArrayList<>(EnumSet.allOf(TipoEvento.class)));
//        model.addAttribute("menues", menuService.findAll());
        model.addAttribute("locales", localService.findAll());
        model.addAttribute("cupones", cuponService.findAll());
        model.addAttribute("usuarios",  usuarioService.findAll());
    }
}




