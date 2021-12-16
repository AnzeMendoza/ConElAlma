package com.gylgroup.conelalma.controllers;

import javax.validation.Valid;

import com.gylgroup.conelalma.entities.Rol;
import com.gylgroup.conelalma.entities.Usuario;
import com.gylgroup.conelalma.services.RolService;
import com.gylgroup.conelalma.services.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    RolService rolService;

    @GetMapping("/todos")
    public ModelAndView obtenerUsuarios() {

        ModelAndView mav = new ModelAndView("usuarios-formulario");
        mav.addObject("usuarios", usuarioService.findAll());
        mav.addObject("usuario", new Usuario());
        mav.addObject("roles", rolService.findAll());
        mav.addObject("estado", false);// por defecto debe ser false
        mav.addObject("action", "guardarEmpleados");

        return mav;
    }

    @PostMapping("/guardarEmpleados")
    public ModelAndView guardarUsuario(@RequestParam(value = "imagen", required = false) MultipartFile imagen,
            @Valid Usuario usuario, BindingResult bindingResult,
            @RequestParam(required = false) Rol rol,
            RedirectAttributes attributes) {

        ModelAndView mav = new ModelAndView();
        if (bindingResult.hasErrors()) {

            mav.addObject("usuarios", usuarioService.findAll());
            mav.addObject("usuario", usuario);
            mav.addObject("roles", rolService.findAll());
            mav.addObject("estado", true);
            mav.addObject("action", "guardarEmpleados");
            mav.setViewName("usuarios-formulario");
        } else {

            try {

                usuarioService.save(usuario, rol, imagen);
                attributes.addFlashAttribute("exito", "REGISTRO CON EXITO!");
                mav.setViewName("redirect:/usuario/todos");
            } catch (Exception e) {

                attributes.addFlashAttribute("error", e.getMessage());
                mav.setViewName("redirect:/usuario/todos");
            }

        }

        return mav;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView formEditarUsuario(@PathVariable Integer id) {

        ModelAndView mav = new ModelAndView("usuarios-formulario");
        mav.addObject("usuarios", usuarioService.findAll());
        mav.addObject("usuario", usuarioService.finById(id));
        mav.addObject("roles", rolService.findAll());
        mav.addObject("estado", true);
        mav.addObject("action", "modificar");
        return mav;
    }

    @PostMapping("/modificar")
    public ModelAndView modificarUsuario(@RequestParam(value = "imagen", required = false) MultipartFile imagen,
            @RequestParam Integer id, @Valid Usuario usuario, BindingResult bindingResult,
            @RequestParam Rol rol,
            RedirectAttributes attributes) {

        ModelAndView mav = new ModelAndView();

        if (bindingResult.hasErrors()) {

            mav.addObject("usuarios", usuarioService.findAll());
            mav.addObject("usuario", usuario);
            mav.addObject("roles", rolService.findAll());
            mav.addObject("estado", true);
            mav.addObject("action", "modificar");
            mav.setViewName("usuarios-formulario");
        } else {

            try {
                // System.err.println("div " + 5 / 0);
                usuarioService.update(id, usuario, rol, imagen);
                attributes.addFlashAttribute("exito", "MODIFICACION EXITOSA!");
                mav.setViewName("redirect:/usuario/todos");
            } catch (Exception e) {

                attributes.addFlashAttribute("error", e.getMessage());
                mav.setViewName("redirect:/usuario/todos");
            }

        }

        return mav;
    }

    @PostMapping("/eliminar/{id}")
    public RedirectView eliminarUsuario(@PathVariable Integer id, RedirectAttributes attributes) {

        RedirectView redirectView = new RedirectView("/usuario/todos");
        try {

            usuarioService.disable(id);
            attributes.addFlashAttribute("exito", "USUARIO ELIMINADO CON EXITO!");
        } catch (Exception e) {

            attributes.addFlashAttribute("error", e.getMessage());
            redirectView.setUrl("/usuario/todos");
        }

        return redirectView;
    }

    /* registro unico del cliente */
    @GetMapping("/registrarse")
    public ModelAndView formRegistroCliente() {

        ModelAndView mav = new ModelAndView("signup");
        mav.addObject("usuario", new Usuario());
        return mav;
    }

    @PostMapping("/guardar")
    public ModelAndView guardarCliente(@RequestParam(value = "imagen", required = false) MultipartFile imagen,
            @Valid Usuario usuario,
            BindingResult bindingResult,
            RedirectAttributes attributes) {

        ModelAndView mav = new ModelAndView();

        if (bindingResult.hasErrors()) {

            mav.setViewName("signup");
        } else {

            try {

                usuarioService.save(usuario, null, imagen);
                attributes.addFlashAttribute("exito", "REGISTRO CON EXITO!");
                mav.setViewName("redirect:/usuario/registrarse");
            } catch (Exception e) {

                attributes.addFlashAttribute("error", e.getMessage());
                mav.setViewName("redirect:/usuario/registrarse");
            }

        }
        return mav;
    }

}
