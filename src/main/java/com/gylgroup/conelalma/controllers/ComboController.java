package com.gylgroup.conelalma.controllers;

import com.gylgroup.conelalma.entities.Combo;
import com.gylgroup.conelalma.entities.Usuario;
import com.gylgroup.conelalma.services.BebidaService;
import com.gylgroup.conelalma.services.ComboService;
import com.gylgroup.conelalma.services.ComidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/combos")
public class ComboController {

    @Autowired
    private ComboService comboService;
    @Autowired
    private BebidaService bebidaService;
    @Autowired
    private ComidaService comidaService;
    
    @GetMapping("/")
    public ModelAndView listarTodos(HttpSession session){
        ModelAndView mav = new ModelAndView("public/combos");
        Usuario user = (Usuario) session.getAttribute("user");
        if(user.getRol().getNombre().equals("CLIENTE")){
            mav.addObject("combos", comboService.findAllAndEstado());
            mav.addObject("usuario",user);
        }else{
            //FALTA VISTA PARA ADMIN DE COMBOS
        }

        return mav;
    }
    
    @GetMapping("/crear")
    public ModelAndView crear(){
        ModelAndView mav = new ModelAndView("combosFormulario.html");
        mav.addObject("combo",new Combo());
        mav.addObject("comidas", comidaService.findAllByEstado());
//        mav.addObject("bebidas", bebidaService.findAll());
        mav.addObject("title", "Crear Combo");
        mav.addObject("action","guardar");
        return mav;
    }
    
    @PostMapping("/guardar")
    public RedirectView guardar(@ModelAttribute Combo combo, RedirectAttributes attributes){
        RedirectView rv = new RedirectView("/locales");
        try{
            comboService.save(combo);
        }catch(Exception e){
            rv.setUrl("/combos/crear");
        }
        return rv;
    }
    
    @GetMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable Integer id){
        ModelAndView mav = new ModelAndView("combosFormulario.html");
        Combo optional = comboService.findById(id);
        if(optional != null){
            mav.addObject("combo", comboService.findById(id));
            mav.addObject("title","Editar Local");
            mav.addObject("action", "modificar");
        }
        return mav;
    }
    
    @PostMapping("/modificar")
    public RedirectView modificar(@ModelAttribute Combo combo, RedirectAttributes attributes){
        RedirectView redirectView = new RedirectView("/Combos");
        comboService.update(combo);
        return redirectView;
    }
    
    @GetMapping("/baja/{id}")
    public RedirectView eliminar(@PathVariable Integer id){
        comboService.disable(id);
        return new RedirectView("/combos");
    }
    
    @GetMapping("/alta/{id}")
    public RedirectView Dardealta(@PathVariable Integer id){
        comboService.enable(id);
        return new RedirectView("/combos");
    }
}
