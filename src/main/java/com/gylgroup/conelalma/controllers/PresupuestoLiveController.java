package com.gylgroup.conelalma.controllers;

import com.gylgroup.conelalma.entities.Menu;
import com.gylgroup.conelalma.entities.PresupuestoLive;
import com.gylgroup.conelalma.entities.Usuario;
import com.gylgroup.conelalma.exception.ExceptionService;
import com.gylgroup.conelalma.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
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
    public ModelAndView crearPresupuesto(HttpSession session, HttpServletRequest request) throws Exception {
        Usuario user = (Usuario) session.getAttribute("user");
        Map<String,?> map = RequestContextUtils.getInputFlashMap(request);
        Menu menu = (Menu) map.get("menu");
        ModelAndView mav = new ModelAndView();

        if(user.getRol().getNombre().equals("CLIENTE")){
            mav.setViewName("public/presupuesto-formulario");
            mav.addObject("usuario",user);

        }
        return mav;
    }
}




