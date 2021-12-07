package com.gylgroup.conelalma.controllers;

import com.gylgroup.conelalma.entities.Comida;
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

@Controller
@RequestMapping("/Comidas")
public class ComidaController {
    @Autowired
    private ComidaService comidaService;
    
    @GetMapping()
    public ModelAndView misComidas(){
        ModelAndView mav = new ModelAndView("");
        mav.addObject("comidas", comidaService.findAll());
        return mav;
    }
    @GetMapping("/crear")
    public ModelAndView crearComida(){
        ModelAndView mav = new ModelAndView("");
        mav.addObject("title", "Crear Comida");
        mav.addObject("action", "guardar");
        mav.addObject("comida", new Comida());
        return mav;   
    }
    
    @PostMapping("/guardar")
    public RedirectView guardar(@ModelAttribute Comida comida, RedirectAttributes atributes){
        RedirectView rv = new RedirectView("/Comidas");
        try{
            comidaService.save(comida);
        }catch(Exception e){
            rv.addStaticAttribute("comida", comida);
            rv.setUrl("/Comidas/crear"); 
        }
        return rv;
    }
    
    @GetMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable Integer id){
        ModelAndView mav = new ModelAndView("");
        if(comidaService.exitsById(id)){
            mav.addObject("comida", comidaService.findById(id));
            mav.addObject("title","Editar Comida");
            mav.addObject("action", "modificar");
        }
        return mav;
    }
    
    @PostMapping("/modificar")
    public RedirectView modificar(@ModelAttribute Comida comida, RedirectAttributes attributes){
        RedirectView redirectView = new RedirectView("/Comidas");
        comidaService.update(comida);
        return redirectView;
    }
    
    @GetMapping("/eliminar/{id}")
    public RedirectView eliminarLocal(@PathVariable Integer id){
        comidaService.disable(id);
        return new RedirectView("/Comidas");
    }
    
}
