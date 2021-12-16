package com.gylgroup.conelalma.controllers;

import com.gylgroup.conelalma.entities.Comida;
import com.gylgroup.conelalma.services.ComidaService;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/comidas")
public class ComidaController {

    @Autowired
    private ComidaService comidaService;

    @GetMapping("/")
    public ModelAndView misComidas() {
        ModelAndView mav = new ModelAndView("comidaList.html");
        mav.addObject("comidas", comidaService.findAll());
        mav.addObject("comidasActivas", comidaService.findAllByEstado());
        return mav;
    }

    @GetMapping("/crear")
    public ModelAndView crearComida() {
        ModelAndView mav = new ModelAndView("comidaForm.html");
        mav.addObject("title", "Crear Comida");
        mav.addObject("action", "guardar");
        mav.addObject("comida", new Comida());
        return mav;
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute Comida comida,
                          BindingResult result,
                          Model model) {
        if (result.hasErrors()) {
            model.addAttribute("action", "guardar");
            return "comidaForm";
        }
        comidaService.save(comida);
        return "redirect:/comidas/";
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView("comidaForm.html");
        if (comidaService.existsById(id)) {
            mav.addObject("comida", comidaService.findById(id));
            mav.addObject("title", "Editar Comida");
            mav.addObject("action", "modificar");
        }
        return mav;
    }

    @PostMapping("/modificar")
    public String modificar(@Valid @ModelAttribute Comida comida,
                            BindingResult result,
                            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("action", "modificar");
            return "comidaForm";
        }
        comidaService.update(comida);
        return "redirect:/comidas/";
    }

    @GetMapping("/desactivar/{id}")
    public RedirectView baja(@PathVariable Integer id) {
        comidaService.disable(id);
        return new RedirectView("/comidas/");
    }

    @GetMapping("/activar/{id}")
    public RedirectView Alta(@PathVariable Integer id) {
        comidaService.enable(id);
        return new RedirectView("/comidas/");
    }
}
