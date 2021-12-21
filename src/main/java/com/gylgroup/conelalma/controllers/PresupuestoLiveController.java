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
    public ModelAndView listar() {
        ModelAndView mav = new ModelAndView("presupuesto-formulario");
        mav.addObject("presupuesto", new PresupuestoLive());
        mav.addObject("presupuestos", presupuestoLiveService.findAll());
        mav.addObject("presupuestosActivos", presupuestoLiveService.findByEstadoTrue());
        return mav;
    }

/*    @GetMapping("/agregar/{id}")
    public String prestamoFormulario(Model model,
                                     @PathVariable("id") int id) throws Exception {
        sendDataEntities(model);
        if (id == 0) {
            model.addAttribute("presupuesto", new PresupuestoLive());
        } else {
            model.addAttribute("presupuesto", presupuestoLiveService.findById(id));
        }
        return "presupuestoFormulario";
    }*/

    @PostMapping("/agregar/{id}")
    public String prestamoAlta(Model model,
                               @Valid @ModelAttribute("presupuesto") PresupuestoLive presupuesto,
                               BindingResult result,
                               @PathVariable("id") int id) throws Exception {
        if (result.hasErrors()) {
            sendDataEntities(model);
            return "presupuesto-formulario";
        }

        if (id == 0) {
            presupuestoLiveService.save(presupuesto);
        } else {
            presupuestoLiveService.update(presupuesto, id);
        }
        return "redirect:/presupuesto/todos";
    }

    @GetMapping("/activar/{id}")
    public RedirectView activar(@PathVariable("id") int id) {
        try {
            presupuestoLiveService.enable(id);
        } catch (Exception e) {

        }
        return new RedirectView("/presupuesto/");
    }

    @GetMapping("/desactivar/{id}")
    public RedirectView desactivar(@PathVariable("id") int id) {
        try {
            presupuestoLiveService.disable(id);
        } catch (Exception e) {

        }
        return new RedirectView("/presupuesto/");
    }

    private void sendDataEntities(Model model) throws ExceptionService {
//        model.addAttribute("tiposDeEventos", new ArrayList<>(EnumSet.allOf(TipoEvento.class)));
/*        model.addAttribute("menues", menuService.findAllByEstado());
        model.addAttribute("locales", localService.findAllAndEstado());
        model.addAttribute("cupones", cuponService.findAllAndEstado(true));
        model.addAttribute("usuarios",  usuarioService.findAllByEstado(true));*/
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
        RedirectView reMav = new RedirectView("/");
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




