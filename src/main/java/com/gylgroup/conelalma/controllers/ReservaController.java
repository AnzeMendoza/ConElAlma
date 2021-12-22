package com.gylgroup.conelalma.controllers;

import com.gylgroup.conelalma.entities.Comentario;
import com.gylgroup.conelalma.entities.PresupuestoLive;
import com.gylgroup.conelalma.entities.Reserva;
import com.gylgroup.conelalma.entities.Usuario;
import com.gylgroup.conelalma.enums.TipoDePago;
import com.gylgroup.conelalma.repositories.PresupuestoLiveRepository;
import com.gylgroup.conelalma.services.ComentarioService;
import com.gylgroup.conelalma.services.PresupuestoLiveService;
import com.gylgroup.conelalma.services.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @Autowired
    private PresupuestoLiveService presupuestoService;

    @Autowired
    private ComentarioService comentarioService;

    @GetMapping
    @PreAuthorize("hasAnyRole('CLIENTE')")
    public ModelAndView listaReservas(HttpSession session){
        ModelAndView mav = new ModelAndView("public/reservas");

        if(session.getAttribute("user")!=null){
            Usuario user = (Usuario) session.getAttribute("user");
            List<Comentario> listaPorReserva= comentarioService.findByUsuarioId(user.getId());
            mav.addObject("reservas",reservaService.findByUserId(user.getId()));
            mav.addObject("usuario",user);
            mav.addObject("logueado","true");
            mav.addObject("listaPorReserva",listaPorReserva);
        }else{
            mav.addObject("logueado","false");
        }

        return mav;

    }

    @GetMapping("/crear")
    @PreAuthorize("hasAnyRole('CLIENTE')")
    public ModelAndView save(HttpSession session) throws Exception {
        ModelAndView mav = new ModelAndView("public/reserva-formulario");
        Reserva reserva = new Reserva();
        Usuario user = (Usuario) session.getAttribute("user");
        PresupuestoLive presupuestoLive = presupuestoService.findByIdUsuario(user.getId());
        Double precioFinal;
        Integer descuento = presupuestoLive.getCupon().getDescuento();
        precioFinal= (( Double.parseDouble(String.valueOf(descuento))) /100)*presupuestoLive.getPrecioFinal();
        reserva.setPresupuestoLive(presupuestoLive);
        reserva.setFechaReserva(presupuestoLive.getFechaEventoSolicitada());
        reserva.setPresupuestoLive(presupuestoLive);
        reserva.setUsuario(user);
        presupuestoLive.setPrecioFinal(precioFinal);

        presupuestoService.update(presupuestoLive,presupuestoLive.getId());
        mav.addObject("reserva",reserva);
        mav.addObject("usuario",user);
        mav.addObject("precioFinal",precioFinal);
        mav.addObject("descuento",presupuestoLive.getCupon().getDescuento());
        mav.addObject("action","guardar");
        mav.addObject("presupuestoLive",presupuestoLive);
        mav.addObject("title","Registrar nueva reserva");

        return mav;
    }

    @GetMapping("/editar/{id}")
    @PreAuthorize("hasAnyRole('CLIENTE','ADMIN')")
    public ModelAndView editarReserva(@PathVariable Integer id){
        ModelAndView mav = new ModelAndView("public/reserva-formulario");

        mav.addObject("reserva",reservaService.findById(id));
        mav.addObject("action","modificar");
        mav.addObject("title","Modifcar reserva");

        return mav;

    }

    @PostMapping("/guardar")
    @PreAuthorize("hasAnyRole('ADMIN','CLIENTE')")
    public RedirectView persistirReserva(@ModelAttribute Reserva reserva, HttpSession session){
        RedirectView reMav = new RedirectView("/reservas");
        Usuario user = (Usuario) session.getAttribute("user");
        reserva.setUsuario(user);
        reservaService.save(reserva);
        return reMav;
    }

    @PostMapping("/modifcar")
    @PreAuthorize("hasAnyRole('CLIENTE')")
    public RedirectView modifcarReserva(@ModelAttribute Reserva reserva, RedirectAttributes attributes){
        RedirectView reMav = new RedirectView("/reservas");
        try {
            reservaService.update(reserva.getId(), reserva);
            attributes.addAttribute("estado", true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reMav;
    }

    @PostMapping("/baja/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','CLIENTE')")
    public RedirectView bajaRerserva(@PathVariable Integer id, HttpSession session){
        RedirectView reMav = new RedirectView("/reservas");
        Usuario user = (Usuario) session.getAttribute("user");
        try {
            reservaService.disable(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(user.getRol().getNombre().equals("CLIENTE")){
            reMav.setUrl("/reservas");
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
        try {
            reservaService.enable(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
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