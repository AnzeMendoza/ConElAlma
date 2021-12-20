package com.gylgroup.conelalma.controllers;


import com.gylgroup.conelalma.entities.Reserva;
import com.gylgroup.conelalma.entities.Usuario;
import com.gylgroup.conelalma.services.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @GetMapping
    @PreAuthorize("hasAnyRole('CLIENTE')")
    public ModelAndView listaReservas(HttpSession session){
        ModelAndView mav = new ModelAndView("public/reservas");

        if(session.getAttribute("user")!=null){
            Usuario user = (Usuario) session.getAttribute("user");
            mav.addObject("reservas",reservaService.findAll());
            mav.addObject("usuario",user);
            mav.addObject("logueado","true");
        }else{
            mav.addObject("logueado","false");
        }
        return mav;

    }

    @GetMapping("crear")
    @PreAuthorize("hasAnyRole('CLIENTE')")
    public ModelAndView save(){
        ModelAndView mav = new ModelAndView("reserva-formulario");

        mav.addObject("reserva",new Reserva());
        mav.addObject("action","guardar");
        mav.addObject("title","Registrar nueva reserva");

        return mav;
    }

    @GetMapping("/editar/{id}")
    @PreAuthorize("hasAnyRole('CLIENTE')")
    public ModelAndView editarReserva(@PathVariable Integer id){
        ModelAndView mav = new ModelAndView("reserva-formulario");

        mav.addObject("reserva",reservaService.findById(id));
        mav.addObject("action","modificar");
        mav.addObject("title","Modifcar reserva");

        return mav;

    }

    @PostMapping("/guardar")
    @PreAuthorize("hasAnyRole('ADMIN','CLIENTE')")
    public RedirectView persistirReserva(@ModelAttribute Reserva reserva){
        RedirectView reMav = new RedirectView("/reservas");
        reservaService.save(reserva);
        return reMav;
    }

    @PostMapping("/modifcar")
    @PreAuthorize("hasAnyRole('CLIENTE')")
    public RedirectView modifcarReserva(@ModelAttribute Reserva reserva,RedirectAttributes attributes){
        RedirectView reMav = new RedirectView("/reservas");
        try {
            reservaService.update(reserva);
            attributes.addFlashAttribute("exito", "Modificacion Exitosa");
        } catch (Exception e) {
            attributes.addFlashAttribute("reserva",reservaService.findById(reserva.getId()));
            attributes.addFlashAttribute("error-name",e.getMessage());
            reMav.setUrl("/reservas/editar/"+reserva.getId());
        }

        return reMav;
    }

    @PostMapping("/baja/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','CLIENTE')")
    public RedirectView bajaRerserva(@PathVariable Integer id, HttpSession session){
        RedirectView reMav = new RedirectView("/reservas");
        Usuario user = (Usuario) session.getAttribute("user");
        reservaService.disable(id);
        if(user.getRol().getNombre().equals("CLIENTE")){
            reMav.setUrl("/");
        }else{
            reMav.setUrl("/reservas/todos");

        }
        return reMav;
    }

    @PostMapping("/alta/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','CLIENTE')")
    public RedirectView habilitarRerserva(@PathVariable Integer id,HttpSession session){
        RedirectView reMav = new RedirectView("/reservas");
        Usuario user = (Usuario) session.getAttribute("user");
        reservaService.enable(id);
        if(user.getRol().getNombre().equals("CLIENTE")){
            reMav.setUrl("/");
        }else{
            reMav.setUrl("/reservas/todos");

        }
        return reMav;
    }

    @GetMapping("/todos")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ModelAndView reservasAdmin(){
        ModelAndView mav = new ModelAndView("admin/reservas");
        mav.addObject("reservas",reservaService.findAll());
        return mav;
    }

}
