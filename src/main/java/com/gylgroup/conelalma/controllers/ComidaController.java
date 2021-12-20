package com.gylgroup.conelalma.controllers;

import com.gylgroup.conelalma.entities.Comida;
import com.gylgroup.conelalma.services.ComidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;

@Controller
@RequestMapping("/comidas")
public class ComidaController {

    @Autowired
    private ComidaService comidaService;

    @GetMapping("/todos")
    public ModelAndView misComidas() {
        ModelAndView mav = new ModelAndView("admin/comida-formulario");
        mav.addObject("comidas", comidaService.findAll());
        mav.addObject("comidasActivas", comidaService.findAllByEstado());
        mav.addObject("comida", new Comida());
        mav.addObject("estado", false);// por defecto debe ser false
        mav.addObject("action", "agregar");
        return mav;
    }

    @GetMapping("/agregar")
    public ModelAndView crearComida() {
        ModelAndView mav = new ModelAndView("admin/comida-formulario");
        mav.addObject("comida", new Comida());
        mav.addObject("title", "Crear comida");
        mav.addObject("action", "agregar");
        return mav;
    }

    @PostMapping("/agregar")
    public String guardar(@Valid @ModelAttribute Comida comida,
            BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("comidas", comidaService.findAll());
            model.addAttribute("action", "agregar");
            model.addAttribute("estado", true);
            return "admin/comida-formulario";
        }
        comidaService.save(comida);
        return "redirect:/comidas/todos";
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView("admin/comida-formulario");
        if (comidaService.existsById(id)) {
            mav.addObject("comidas", comidaService.findAll());
            mav.addObject("comida", comidaService.findById(id));
            mav.addObject("action", "editar/" + id);
            mav.addObject("estado", true);
        }
        return mav;
    }

    @PostMapping("/editar/{id}")
    public String modificar(@Valid @ModelAttribute Comida comida,
            BindingResult result,
            Model model,
            @PathVariable Integer id) {
        if (result.hasErrors()) {
            model.addAttribute("comidas", comidaService.findAll());
            model.addAttribute("action", "editar/" + id);
            model.addAttribute("estado", true);
            return "admin/comida-formulario";
        }
        comidaService.update(comida);
        return "redirect:/comidas/todos";
    }

    @PostMapping("/desactivar/{id}")
    public RedirectView baja(@PathVariable Integer id) {
        comidaService.disable(id);
        return new RedirectView("/comidas/todos");
    }

    @PostMapping("/activar/{id}")
    public RedirectView Alta(@PathVariable Integer id) {
        comidaService.enable(id);
        return new RedirectView("/comidas/todos");
    }
}
