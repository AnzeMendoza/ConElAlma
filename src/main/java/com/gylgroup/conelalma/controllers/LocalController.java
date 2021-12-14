package com.gylgroup.conelalma.controllers;

import com.gylgroup.conelalma.entities.Local;
import com.gylgroup.conelalma.services.LocalService;
import java.util.List;
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

@Controller
@RequestMapping("/Locales")
public class LocalController {
    @Autowired
    private LocalService localService;
    
    //Función para retornar el listado de Locales de la DDBB
    @GetMapping()
    public ModelAndView locales(){
        List<Local> misLocales = localService.findAll();
        ModelAndView model = new ModelAndView("prueba.html");
        model.addObject("Locales", misLocales);
        return model;
    }
    
    @GetMapping("/crear")
    public ModelAndView crearLocal(){
        ModelAndView model = new ModelAndView("pruebaform.html");
        model.addObject("local", new Local());
        model.addObject("title","Crear nuevo Local");
        model.addObject("action", "guardar");
        return model;
    }
    
    @PostMapping("/guardar")
    public RedirectView guardarLocal(@ModelAttribute Local local, RedirectAttributes attributes){
        RedirectView rv = new RedirectView("/Locales");
        try{
            localService.save(local);
        }catch(Exception e){
            rv.setUrl("/Locales/crear"); 
        }
        return rv;
    }
    
    @GetMapping("/editar/{id}")
    public ModelAndView editarLocal(@PathVariable Integer id){
        ModelAndView mav = new ModelAndView("pruebaform.html");
        if(localService.existsLocal(id)){
            mav.addObject("local", localService.findById(id));
            mav.addObject("title","Editar Local");
            mav.addObject("action", "modificar");
        }
        return mav;
    }
    
    @PostMapping("/modificar")
    public RedirectView modificarLocal(@ModelAttribute Local local, RedirectAttributes attributes){
        RedirectView redirectView = new RedirectView("/Locales");
        localService.update(local);
        return redirectView;
    }
    
    @GetMapping("/eliminar/{id}")
    public RedirectView eliminarLocal(@PathVariable Integer id){
        localService.disable(id);
        return new RedirectView("/Locales");
    }
    
    @GetMapping("/Alta/{id}")
    public RedirectView altaLocal(@PathVariable Integer id){
        localService.enable(id);
        return new RedirectView("/Locales");
    }
    
}
