package com.gylgroup.conelalma.controllers;

import com.gylgroup.conelalma.entities.Local;
import com.gylgroup.conelalma.services.LocalService;

import java.util.List;

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

import javax.validation.Valid;

@Controller
@RequestMapping("/locales")
public class LocalController {

    @Autowired
    private LocalService localService;

    @GetMapping("/")
    public ModelAndView locales() {
        ModelAndView mav = new ModelAndView("local.html");
        mav.addObject("locales", localService.findAll());
        mav.addObject("localesActivos", localService.findAllAndEstado());
        return mav;
    }

    @GetMapping("/crear")
    public ModelAndView crearLocal() {
        ModelAndView mav = new ModelAndView("localForm.html");
        mav.addObject("local", new Local());
        mav.addObject("title", "Crear nuevo Local");
        mav.addObject("action", "guardar");
        return mav;
    }

    @PostMapping("/guardar")
    public String guardarLocal(@Valid @ModelAttribute Local local,
                               BindingResult result,
                               Model model) {
        if (result.hasErrors()) {
            model.addAttribute("action", "guardar");
            return "localForm";
        }
        localService.save(local);
        return "redirect:/locales/";
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editarLocal(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView("localForm.html");
        if (localService.existsLocal(id)) {
            mav.addObject("local", localService.findById(id));
            mav.addObject("title", "Editar Local");
            mav.addObject("action", "modificar");
        }
        return mav;
    }

    @PostMapping("/modificar")
    public String modificarLocal(@Valid @ModelAttribute Local local,
                                 BindingResult result,
                                 Model model) {
        if(result.hasErrors()){
            model.addAttribute("action", "modificar");
            return "localForm";
        }
        localService.update(local);
        return "redirect:/locales/";
    }

    @GetMapping("/desactivar/{id}")
    public RedirectView bajaLocal(@PathVariable Integer id) {
        localService.disable(id);
        return new RedirectView("/locales/");
    }

    @GetMapping("/activar/{id}")
    public RedirectView altaLocal(@PathVariable Integer id) {
        localService.enable(id);
        return new RedirectView("/locales/");
    }
}
