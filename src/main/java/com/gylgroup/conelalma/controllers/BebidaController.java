package com.gylgroup.conelalma.controllers;

import com.gylgroup.conelalma.entities.Bebida;
import com.gylgroup.conelalma.exception.ExceptionService;
import com.gylgroup.conelalma.services.BebidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;

@Controller
@RequestMapping("/bebida")
public class BebidaController {

    @Autowired
    BebidaService bebidaService;

    @GetMapping("/todos")
    public ModelAndView obtenerBebidas() {
        ModelAndView mav = new ModelAndView("admin/bebidas-formulario");
        mav.addObject("estado", false);
        mav.addObject("bebida", new Bebida());
        mav.addObject("bebidas", bebidaService.findAll());
        mav.addObject("action", "agregar");
        return mav;
    }

    @PostMapping("/agregar")
    public String guardarBebida(@Valid Bebida bebida,
                                BindingResult bindingResult,
                                Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("bebidas", bebidaService.findAll());
            model.addAttribute("bebida", bebida);
            model.addAttribute("action", "agregar");
            model.addAttribute("estado", true);
            return "admin/bebidas-formulario";
        }
        try {
            bebidaService.save(bebida);
        } catch (ExceptionService e) {
            e.printStackTrace();
        }
        return "redirect:/bebida/todos";
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable("id") Integer id) throws Exception {
        ModelAndView mav = new ModelAndView("admin/bebidas-formulario");
        if (bebidaService.existsById(id)) {
            mav.addObject("bebidas", bebidaService.findAll());
            mav.addObject("bebida", bebidaService.findById(id));
            mav.addObject("action", "editar/" + id);
            mav.addObject("estado", true);
        }
        return mav;
    }

    @PostMapping("/editar/{id}")
    public String editarBebida(@PathVariable Integer id,
                               @Valid Bebida bebida,
                               BindingResult result,
                               Model model) {
        try {
            if (result.hasErrors()) {
                model.addAttribute("bebidas", bebidaService.findAll());
                model.addAttribute("action", "editar/" + id);
                model.addAttribute("estado", true);
                return "admin/bebidas-formulario";
            }
            bebidaService.update(id, bebida);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "redirect:/bebida/todos";
    }

    @PostMapping("/desactivar/{id}")
    public RedirectView deshabilitar(@PathVariable Integer id) {
        bebidaService.disable(id);
        return new RedirectView("/bebida/todos");
    }

    @PostMapping("/activar/{id}")
    public RedirectView habilitar(@PathVariable Integer id) {
        bebidaService.enable(id);
        return new RedirectView("/bebida/todos");
    }
}
