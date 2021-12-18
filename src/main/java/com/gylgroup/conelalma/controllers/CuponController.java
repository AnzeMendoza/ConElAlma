package com.gylgroup.conelalma.controllers;

import com.gylgroup.conelalma.entities.Cupon;
import com.gylgroup.conelalma.services.CuponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;

@Controller
@RequestMapping("/cupon")
public class CuponController {

    @Autowired
    private CuponService cuponService;

    @GetMapping("/todos")
    public ModelAndView listar() {
        ModelAndView mav = new ModelAndView("cupon-formulario");
        mav.addObject("cupones", cuponService.findAll());
        mav.addObject("cuponesActivos", cuponService.findAllAndEstado(true));
        mav.addObject("cupon", new Cupon());
        mav.addObject("estado", false);// por defecto debe ser false
        mav.addObject("action", "agregar");
        return mav;
    }

    @GetMapping("/agregar")
    public ModelAndView cuponCreate() {
        ModelAndView mav = new ModelAndView("cupon-formulario");
        mav.addObject("cupon", new Cupon());
        mav.addObject("title", "Crear cupon");
        mav.addObject("action", "agregar");
        return mav;
    }

    @PostMapping("/agregar")
    public String cuponSave(@Valid @ModelAttribute Cupon cupon,
                            BindingResult result,
                            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("action", "agregar");
            model.addAttribute("cupones", cuponService.findAll());
            model.addAttribute("cuponesActivos", cuponService.findAllAndEstado(true));
            model.addAttribute("estado", true);
            return "cupon-formulario";
        }
        cuponService.save(cupon);
        return "redirect:/cupon/todos";
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable("id") Integer id) throws Exception {
        ModelAndView mav = new ModelAndView("cupon-formulario");
        if (cuponService.existsById(id)) {
            mav.addObject("cupones", cuponService.findAll());
            mav.addObject("cupon", cuponService.findById(id));
            mav.addObject("action", "editar/" + id);
            mav.addObject("estado", true);
        }
        return mav;
    }

    @PostMapping("/editar/{id}")
    public String cuponUpdate(@PathVariable Integer id,
                              @Valid Cupon cupon,
                              BindingResult result,
                              Model model) {
        try {
            if (result.hasErrors()) {
                model.addAttribute("cupones", cuponService.findAll());
                model.addAttribute("action", "editar/" + id);
                model.addAttribute("estado", true);
                return "cupon-formulario";
            }
            cuponService.update(id, cupon);
            model.addAttribute("estado", true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "redirect:/cupon/todos";
    }

    @PostMapping("/activar/{id}")
    public RedirectView activar(@PathVariable("id") int id) {
        cuponService.enable(id);
        return new RedirectView("/cupon/todos");
    }

    @PostMapping("/desactivar/{id}")
    public RedirectView desactivar(@PathVariable("id") int id) {
        cuponService.disable(id);
        return new RedirectView("/cupon/todos");
    }
}

