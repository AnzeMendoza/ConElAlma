package com.gylgroup.conelalma.controllers;

import javax.validation.Valid;

import com.gylgroup.conelalma.entities.Rol;
import com.gylgroup.conelalma.services.RolService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/rol")
public class RolController {

    @Autowired
    RolService rolService;

    @GetMapping("/todos")
    public ModelAndView obtenerRoles() {

        ModelAndView mav = new ModelAndView("/admin/roles-formulario");
        mav.addObject("roles", rolService.findAll());
        mav.addObject("rol", new Rol());
        mav.addObject("estado", false);
        mav.addObject("action", "guardar");

        return mav;
    }

    @PostMapping("/guardar")
    public ModelAndView guardarRol(@Valid Rol rol, BindingResult bindingResult, RedirectAttributes attributes) {

        ModelAndView mav = new ModelAndView();

        if (bindingResult.hasErrors()) {

            mav.addObject("roles", rolService.findAll());
            mav.addObject("rol", rol);
            mav.addObject("estado", true);
            mav.addObject("action", "guardar");
            mav.setViewName("/admin/roles-formulario");
        } else {

            try {

                rolService.save(rol);
                attributes.addFlashAttribute("exito", "REGISTRO CON EXITO!");
                mav.setViewName("redirect:/rol/todos");
            } catch (Exception e) {

                attributes.addFlashAttribute("error", e.getMessage());
                mav.setViewName("redirect:/rol/todos");
            }
        }
        return mav;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView formEditarRol(@PathVariable Integer id) {

        ModelAndView mav = new ModelAndView("/admin/roles-formulario");
        mav.addObject("roles", rolService.findAll());
        mav.addObject("rol", rolService.findById(id));
        mav.addObject("estado", true);
        mav.addObject("action", "modificar");
        return mav;
    }

    @PostMapping("/modificar")
    public ModelAndView modificarRol(@RequestParam Integer id, @Valid Rol rol, BindingResult bindingResult,
            RedirectAttributes attributes) {

        ModelAndView mav = new ModelAndView();

        if (bindingResult.hasErrors()) {

            mav.addObject("roles", rolService.findAll());
            mav.addObject("rol", rol);
            mav.addObject("estado", true);
            mav.addObject("action", "modificar");
            mav.setViewName("/admin/roles-formulario");
        } else {

            try {

                rolService.update(id, rol);
                attributes.addFlashAttribute("exito", "MODIFICACION EXITOSA!");
                mav.setViewName("redirect:/rol/todos");
            } catch (Exception e) {

                attributes.addFlashAttribute("error", e.getMessage());
                mav.setViewName("redirect:/rol/todos");
            }

        }

        return mav;
    }

    @PostMapping("/deshabilitar/{id}")
    public ModelAndView deshabilitarRol(@PathVariable Integer id, RedirectAttributes attributes) {

        ModelAndView mav = new ModelAndView();
        try {

            rolService.disable(id);
            attributes.addFlashAttribute("exito", "ROL DESHABILITADO CON EXITO!");
            mav.setViewName("redirect:/rol/todos");
        } catch (Exception e) {

            attributes.addFlashAttribute("error", e.getMessage());
            mav.setViewName("redirect:/rol/todos");
        }

        return mav;
    }

    @PostMapping("/habilitar/{id}")
    public ModelAndView habilitarRol(@PathVariable Integer id, RedirectAttributes attributes) {

        ModelAndView mav = new ModelAndView();
        try {

            rolService.enable(id);
            attributes.addFlashAttribute("exito", "ROL HABILITADO CON EXITO!");
            mav.setViewName("redirect:/rol/todos");
        } catch (Exception e) {

            attributes.addFlashAttribute("error", e.getMessage());
            mav.setViewName("redirect:/rol/todos");
        }

        return mav;
    }

}
