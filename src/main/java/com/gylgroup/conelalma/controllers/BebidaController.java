package com.gylgroup.conelalma.controllers;

import com.gylgroup.conelalma.entities.Bebida;
import com.gylgroup.conelalma.services.BebidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/bebida")
public class BebidaController {

    @Autowired
    BebidaService service;

    @GetMapping("/todos")
    public ModelAndView obtenerBebidas(){

        ModelAndView mav = new ModelAndView("bebidas-formulario");
        mav.addObject("bebidas", service.findAll());

        return mav;
    }


    @GetMapping("/activo")
    public ModelAndView bebidasActivas(){
        ModelAndView mav = new ModelAndView("bebidas-activas");
        List<Bebida> bebidasActivas = null;

        try{
             bebidasActivas = service.findAllByEstado();

        }catch(Exception e){
            e.printStackTrace();
        }
        mav.addObject("bebidas-activas",bebidasActivas);
        return mav;
    }


    @PostMapping("/guardar")
    public ModelAndView guardarBebida(@Valid Bebida bebida, BindingResult bindingResult, RedirectAttributes redirectAttributes){

        ModelAndView mav = new ModelAndView();

        if (bindingResult.hasErrors()){

            mav.addObject("bebidas", service.findAll());
            mav.addObject("bebida",bebida);
            mav.addObject("action","guardar");
            mav.setViewName("bebidas-formulario");
        }else{

            try{
                service.save(bebida);
                redirectAttributes.addFlashAttribute("exito", "Bebida guardada");
                mav.setViewName("redirect:/bebida/todos");
            }catch(Exception e){
                    redirectAttributes.addFlashAttribute("error", e.getMessage());
                    mav.setViewName("redirect:/bebida/todos");
            }

        }
        return mav;
    }


    @PostMapping("/editar/{id}")
    public RedirectView editarBebida(@PathVariable Integer id, Bebida bebida) {

        try {
            service.update(id, bebida);

        } catch (Exception ex) {

            ex.printStackTrace();
        }
        return new RedirectView("/bebida/todos");
    }

    @PostMapping("/modificar")
    public ModelAndView modificarBebida (@RequestParam Integer id, @Valid Bebida bebida, BindingResult bindingResult, RedirectAttributes redirectAttributes){

        ModelAndView mav = new ModelAndView();

        if (bindingResult.hasErrors()){
            mav.addObject("bebidas", service.findAll());
            mav.addObject("bebida",bebida);
            mav.addObject("action","modificar");
            mav.setViewName("bebidas-formulario");
        }else{

            try{
                service.update(id,bebida);
                redirectAttributes.addFlashAttribute("exito", "Modificacion Realizada!");
                mav.setViewName("redirect:/bebida/todos");


            }catch(Exception e){

                redirectAttributes.addFlashAttribute("error",e.getMessage());
            }
        }
        return mav;
    }

    @PostMapping("/eliminar/{id}")
    public RedirectView eliminarBebida(@PathVariable Integer id, RedirectAttributes attributes) {

        RedirectView redirectView = new RedirectView("/bebida/todos");
        try {

            service.disable(id);
            attributes.addFlashAttribute("exito", "Bebida eliminada!");
        } catch (Exception e) {

            attributes.addFlashAttribute("error", e.getMessage());
            redirectView.setUrl("/bebida/todos");
        }

        return redirectView;
    }

}
