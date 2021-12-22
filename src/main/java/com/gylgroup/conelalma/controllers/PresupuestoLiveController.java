package com.gylgroup.conelalma.controllers;


import com.gylgroup.conelalma.entities.*;
import com.gylgroup.conelalma.exception.ExceptionService;
import com.gylgroup.conelalma.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

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

    @Autowired
    private ReservaService reservaService;

    @Autowired
    private ComboService comboService;

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

    @GetMapping("/crear")
    public ModelAndView crearPresupuesto(HttpSession session, HttpServletRequest request, RedirectAttributes attributes) throws Exception {
        Usuario user = (Usuario) session.getAttribute("user");
        Map<String,?> map = RequestContextUtils.getInputFlashMap(request);
        PresupuestoLive presupuesto = new PresupuestoLive();
        ModelAndView mav = new ModelAndView("public/presupuesto-formulario");
        mav = cargarObjetos(user,presupuesto,map);
        request.setAttribute("presupuesto",presupuesto);
        attributes.addFlashAttribute("presupuesto",presupuesto);
        if(user.getRol().getNombre().equals("CLIENTE")){
            mav.setViewName("public/presupuesto-formulario");
        }
        return mav;
    }

    @PostMapping("/savePresupuestoUser")
    public RedirectView guardarPresupuestoUser(RedirectAttributes attributes,@ModelAttribute("presupuestoLive") PresupuestoLive presupuesto, HttpSession session){
        RedirectView reMav = new RedirectView("/reservas/crear");
        presupuesto.setUsuario((Usuario) session.getAttribute("user"));
        presupuesto.setCantidadComensales(presupuesto.getMenu().getCantidadBaseComensales());
        presupuestoLiveService.save(presupuesto);

        attributes.addFlashAttribute("exito", "Presupuesto agregado con exito");
        return reMav;
    }

    private double precioMenu(Menu menu){
        Double precioBase=menu.getCantidadBaseComensales()*menu.getCombo().get(0).getPrecioCombo();

        return precioBase;
    }


    private ModelAndView cargarObjetos(Usuario user, PresupuestoLive presupuestoLive, Map map){
        ModelAndView mav = new ModelAndView();
        //CARGA DE RESERVA
        Menu menu = (Menu) map.get("menu");
        presupuestoLive.setMenu(menuService.findById(menu.getId()));
        presupuestoLive.setCantidadComensales(menu.getCantidadBaseComensales());
        presupuestoLive.setTipoEvento(menu.getTipoEvento().name());
        presupuestoLive.setPrecioFinal(precioMenu(menu));
        presupuestoLive.setUsuario(user);
        mav.addObject("usuario",user);
        mav.addObject("presupuestoLive", presupuestoLive);

        return mav;
    }
}
