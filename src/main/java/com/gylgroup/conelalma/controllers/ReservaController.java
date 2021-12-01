package com.gylgroup.conelalma.controllers;


import com.gylgroup.conelalma.entities.Reserva;
import com.gylgroup.conelalma.services.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @GetMapping
    public ModelAndView listaReservas(){
        ModelAndView mav = new ModelAndView("lista_reservas");
        mav.addObject("listaRerservas",reservaService.traerTodas());
        return mav;
    }

    @GetMapping("crear")
    public ModelAndView crearReserva(){
        ModelAndView mav = new ModelAndView("reserva-formulario");

        mav.addObject("reserva",new Reserva());
        mav.addObject("action","guardar");
        mav.addObject("title","Registrar nueva reserva");

        return mav;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editarReserva(@PathVariable Integer id){
        ModelAndView mav = new ModelAndView("reserva-formulario");

        mav.addObject("reserva",reservaService.buscarPorId(id));
        mav.addObject("action","modificar");
        mav.addObject("title","Modifcar reserva");

        return mav;

    }

    @PostMapping("/guardar")
    public RedirectView persistirReserva(@ModelAttribute Reserva reserva){
        RedirectView reMav = new RedirectView("/reservas");
        reservaService.crearReserva(reserva);
        return reMav;
    }

    @PostMapping("/modifcar")
    public RedirectView modifcarReserva(@ModelAttribute Reserva reserva,RedirectAttributes attributes){
        RedirectView reMav = new RedirectView("/reservas");
        try {
            reservaService.modificarReserva(reserva);
            attributes.addFlashAttribute("exito", "Modificacion Exitosa");
        } catch (Exception e) {
            attributes.addFlashAttribute("reserva",reservaService.buscarPorId(reserva.getId()));
            attributes.addFlashAttribute("error-name",e.getMessage());
            reMav.setUrl("/reservas/editar/"+reserva.getId());
        }

        return reMav;
    }

    @PostMapping("/baja/{id}")
    public RedirectView bajaRerserva(@PathVariable Integer id){
        RedirectView reMav = new RedirectView("/reservas");
        reservaService.bajaReserva(id);
        return reMav;
    }

    @PostMapping("/alta/{id}")
    public RedirectView habilitarRerserva(@PathVariable Integer id){
        RedirectView reMav = new RedirectView("/reservas");
        reservaService.altaReserva(id);
        return reMav;
    }
}
