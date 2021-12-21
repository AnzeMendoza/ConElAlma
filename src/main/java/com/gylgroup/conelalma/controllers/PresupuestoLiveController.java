package com.gylgroup.conelalma.controllers;

import com.gylgroup.conelalma.entities.PresupuestoLive;
import com.gylgroup.conelalma.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/todos")
    public String listar(Model model) {
        sendDataEntities(model);
        model.addAttribute("presupuesto", new PresupuestoLive());
        model.addAttribute("estado", false);
        model.addAttribute("presupuestos", presupuestoLiveService.findAll());
        model.addAttribute("action", "agregar");
        return "admin/presupuesto-formulario";
    }

    @PostMapping("/agregar")
    public String prestamoAlta(@Valid @ModelAttribute("presupuesto") PresupuestoLive presupuesto,
                               BindingResult result,
                               Model model) throws Exception {
        if (result.hasErrors()) {
            sendDataEntities(model);
            model.addAttribute("action", "agregar");
            model.addAttribute("estado", true);
            model.addAttribute("presupuestos", presupuestoLiveService.findAll());
            model.addAttribute("presupuesto", presupuesto);
            return "admin/presupuesto-formulario";
        }
        presupuestoLiveService.save(presupuesto);
        return "redirect:/presupuesto/todos";
    }

    @GetMapping("/editar/{id}")
    public String editar(Model model, @PathVariable("id") Integer id) throws Exception {
        if (presupuestoLiveService.existsById(id)) {
            sendDataEntities(model);
            model.addAttribute("presupuestos", presupuestoLiveService.findAll());
            model.addAttribute("presupuesto", presupuestoLiveService.findById(id));
            model.addAttribute("action", "editar/" + id);
            model.addAttribute("estado", true);
        }
        return "admin/presupuesto-formulario";
    }

    @PostMapping("/editar/{id}")
    public String editarpresupuesto(@Valid PresupuestoLive presupuestoLive,
                                    BindingResult result,
                                    @PathVariable Integer id,
                                    Model model) {
        try {
            if (result.hasErrors()) {
                sendDataEntities(model);
                model.addAttribute("action", "editar/" + id);
                model.addAttribute("presupuesto", presupuestoLiveService.findById(id));
                model.addAttribute("presupuestos", presupuestoLiveService.findAll());
                model.addAttribute("estado", true);
                return "admin/presupuesto-formulario";
            }
            presupuestoLiveService.update(id, presupuestoLive);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "redirect:/presupuesto/todos";
    }

    @PostMapping("/activar/{id}")
    public RedirectView activar(@PathVariable("id") int id) {
        try {
            presupuestoLiveService.enable(id);
        } catch (Exception e) {
            e.getMessage();
        }
        return new RedirectView("/presupuesto/todos");
    }

    @PostMapping("/desactivar/{id}")
    public RedirectView desactivar(@PathVariable("id") int id) {
        try {
            presupuestoLiveService.disable(id);
        } catch (Exception e) {
            e.getMessage();
        }
        return new RedirectView("/presupuesto/todos");
    }

    private void sendDataEntities(Model model)  {
        model.addAttribute("menues", menuService.findAllByEstado(true));
        model.addAttribute("locales", localService.findByEstado(true));
        model.addAttribute("cupones", cuponService.findAllAndEstado(true));
        model.addAttribute("usuarios", usuarioService.findAllByEstado(true));
    }
}