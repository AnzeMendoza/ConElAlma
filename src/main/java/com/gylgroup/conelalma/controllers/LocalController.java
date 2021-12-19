package com.gylgroup.conelalma.controllers;

import com.gylgroup.conelalma.entities.Local;
import com.gylgroup.conelalma.services.LocalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;

@Controller
@RequestMapping("/locales")
public class LocalController {

    @Autowired
    private LocalService localService;

    @GetMapping("/todos")
    public ModelAndView locales() {
        ModelAndView mav = new ModelAndView("admin/local-formulario");
        mav.addObject("locales", localService.findAll());
        mav.addObject("localesActivos", localService.findAllAndEstado());
        mav.addObject("local", new Local());
        mav.addObject("estado", false);// por defecto debe ser false
        mav.addObject("action", "agregar");
        return mav;
    }

    @GetMapping("/agregar")
    public ModelAndView crearLocal() {
        ModelAndView mav = new ModelAndView("admin/local-formulario");
        mav.addObject("local", new Local());
        mav.addObject("title", "Crear nuevo Local");
        mav.addObject("action", "agregar");
        return mav;
    }

    @PostMapping("/agregar")
    public String guardarLocal(@Valid @ModelAttribute Local local,
            BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("locales", localService.findAll());
            model.addAttribute("localesActivos", localService.findAllAndEstado());
            model.addAttribute("action", "agregar");
            model.addAttribute("estado", true);
            return "admin/local-formulario";
        }
        localService.save(local);
        return "redirect:/locales/todos";
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editarLocal(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView("admin/local-formulario");
        if (localService.existsLocal(id)) {
            mav.addObject("locales", localService.findAll());
            mav.addObject("local", localService.findById(id));
            mav.addObject("action", "editar/" + id);
            mav.addObject("estado", true);
        }
        return mav;
    }

    @PostMapping("/editar/{id}")
    public String modificarLocal(@PathVariable Integer id,
                                 @Valid @ModelAttribute Local local,
                                 BindingResult result,
                                 Model model) {
        if(result.hasErrors()){
            model.addAttribute("locales", localService.findAll());
            model.addAttribute("localesActivos", localService.findAllAndEstado());
            model.addAttribute("action", "editar/" + id);
            model.addAttribute("estado", true);
            return "admin/local-formulario";
        }
        localService.update(local);
        return "redirect:/locales/todos";
    }

    @PostMapping("/desactivar/{id}")
    public RedirectView bajaLocal(@PathVariable Integer id) {
        localService.disable(id);
        return new RedirectView("/locales/todos");
    }

    @PostMapping("/activar/{id}")
    public RedirectView altaLocal(@PathVariable Integer id) {
        localService.enable(id);
        return new RedirectView("/locales/todos");
    }
}
