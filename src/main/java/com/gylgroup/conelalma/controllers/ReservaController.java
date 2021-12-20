package com.gylgroup.conelalma.controllers;

import com.gylgroup.conelalma.entities.Reserva;
import com.gylgroup.conelalma.enums.TipoDePago;
import com.gylgroup.conelalma.services.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.EnumSet;

@Controller
@RequestMapping("/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @GetMapping("/todos")
    public ModelAndView listarReservas() {
        ModelAndView mav = new ModelAndView("admin/reserva-formulario");
        mav.addObject("reservas", reservaService.findAll());
        mav.addObject("reserva", new Reserva());
        mav.addObject("tiposDePagos", new ArrayList<>(EnumSet.allOf(TipoDePago.class)));
        mav.addObject("estado", false);
        mav.addObject("action", "agregar");
        return mav;
    }

    @GetMapping("/agregar")
    public ModelAndView reservaCreate() {
        ModelAndView mav = new ModelAndView("admin/reserva-formulario");
        mav.addObject("reserva", new Reserva());
        mav.addObject("action", "agregar");
        return mav;
    }

    @PostMapping("/agregar")
    public String persistirReserva(@Valid Reserva reserva,
                                   BindingResult result,
                                   Model model) {
        if (result.hasErrors()) {
            model.addAttribute("action", "agregar");
            model.addAttribute("reservas", reservaService.findAll());
            model.addAttribute("tiposDePagos", new ArrayList<>(EnumSet.allOf(TipoDePago.class)));
            model.addAttribute("estado", true);
            return "admin/reserva-formulario";
        }
        reservaService.save(reserva);
        return "redirect:/reservas/todos";
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable("id") Integer id) throws Exception {
        ModelAndView mav = new ModelAndView("admin/reserva-formulario");
        if (reservaService.existsById(id)) {
            mav.addObject("reservas", reservaService.findAll());
            mav.addObject("reserva", reservaService.findById(id));
            mav.addObject("tiposDePagos", new ArrayList<>(EnumSet.allOf(TipoDePago.class)));
            mav.addObject("action", "editar/" + id);
            mav.addObject("estado", true);
        }
        return mav;
    }

    @PostMapping("/editar/{id}")
    public String reservaUpdate(@PathVariable Integer id,
                                @Valid @ModelAttribute Reserva reserva,
                                BindingResult result,
                                Model model) {
        if (result.hasErrors()) {
            model.addAttribute("reservas", reservaService.findAll());
            model.addAttribute("action", "editar/" + id);
            model.addAttribute("estado", true);
            return "admin/reserva-formulario";
        }
        try {
            reservaService.update(id, reserva);
            model.addAttribute("estado", true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/reservas/todos";
    }

    @PostMapping("/activar/{id}")
    public RedirectView bajaRerserva(@PathVariable Integer id) {
        try {
            reservaService.enable(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new RedirectView("/reservas/todos");
    }

    @PostMapping("/desactivar/{id}")
    public RedirectView habilitarRerserva(@PathVariable Integer id) {
        try {
            reservaService.disable(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new RedirectView("/reservas/todos");
    }
}