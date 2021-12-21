package com.gylgroup.conelalma.controllers;

import com.gylgroup.conelalma.entities.Combo;
import com.gylgroup.conelalma.entities.Menu;
import com.gylgroup.conelalma.entities.Usuario;
import com.gylgroup.conelalma.exception.ExceptionService;
import com.gylgroup.conelalma.services.ComboService;
import com.gylgroup.conelalma.services.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService service;

    @Autowired
    private ComboService comboService;

    @GetMapping("/todos")
    public ModelAndView obtenerMenu(){

        ModelAndView mav = new ModelAndView("menus-formulario");
        mav.addObject("menus", service.findAll());

        return mav;
    }

    @GetMapping("/activo")
    public ModelAndView menusActivos(){
        ModelAndView mav = new ModelAndView("menu-activos");
        List<Menu> menuActivos = null;

        try{
            menuActivos = service.findAllByEstado();

        }catch(Exception e){
            e.printStackTrace();
        }
        mav.addObject("menu-activos",menuActivos);
        return mav;
    }

    @PostMapping("/guardar")
    public ModelAndView guardarMenu(@Valid Menu menu, BindingResult bindingResult, RedirectAttributes redirectAttributes){

        ModelAndView mav = new ModelAndView();

        if (bindingResult.hasErrors()){

            mav.addObject("menus", service.findAll());
            mav.addObject("menu",menu);
            mav.addObject("action","guardar");
            mav.setViewName("menus-formulario");
        }else{

            try{
                service.save(menu);
                redirectAttributes.addFlashAttribute("exito", "Menu guardado");
                mav.setViewName("redirect:/menu/todos");
            }catch(Exception e){
                redirectAttributes.addFlashAttribute("error", e.getMessage());
                mav.setViewName("redirect:/menu/todos");
            }

        }
        return mav;
    }

    @PostMapping("/editar/{id}")
    public RedirectView editarMenu(@PathVariable Integer id, Menu menu) {

        try {
            service.update(id, menu);

        } catch (Exception ex) {

            ex.printStackTrace();
        }
        return new RedirectView("/menu/todos");
    }

    @PostMapping("/modificar")
    public ModelAndView modificarBebida (@RequestParam Integer id, @Valid Menu menu, BindingResult bindingResult, RedirectAttributes redirectAttributes){

        ModelAndView mav = new ModelAndView();

        if (bindingResult.hasErrors()){
            mav.addObject("menus", service.findAll());
            mav.addObject("menu",menu);
            mav.addObject("action","modificar");
            mav.setViewName("menus-formulario");
        }else{

            try{
                service.update(id,menu);
                redirectAttributes.addFlashAttribute("exito", "Modificacion Realizada!");
                mav.setViewName("redirect:/menu/todos");


            }catch(Exception e){

                redirectAttributes.addFlashAttribute("error",e.getMessage());
            }
        }
        return mav;
    }

    @PostMapping("/eliminar/{id}")
    public RedirectView eliminarMenu(@PathVariable Integer id, RedirectAttributes attributes) {

        RedirectView redirectView = new RedirectView("/menu/todos");
        try {

            service.disable(id);
            attributes.addFlashAttribute("exito", "Menu eliminado!");
        } catch (Exception e) {

            attributes.addFlashAttribute("error", e.getMessage());
            redirectView.setUrl("/menu/todos");
        }

        return redirectView;
    }

    @GetMapping("/crear")
    public ModelAndView crearMenu(@RequestParam(required=false,name = "comboid") Integer idCombo, HttpSession session){
        ModelAndView mav = new ModelAndView();
        Usuario user = (Usuario) session.getAttribute("user");
        Menu menu=new Menu();

        if(user.getRol().getNombre().equals("CLIENTE")){
            Combo combo = comboService.findById(idCombo);
            menu.setCombo(Collections.singletonList(comboService.findById(idCombo)));
            mav.setViewName("public/menu-formulario");
            mav.addObject("menu",menu);
            mav.addObject("usuario",user);
            mav.addObject("comboId",idCombo);
        }else{
            mav.setViewName("redirect: /menu/todos");
        }

        return mav;
    }

    @PostMapping (value="/saveMenuUser")
    private RedirectView persistir(@ModelAttribute("menu") Menu menu, RedirectAttributes attributes ) throws ExceptionService {
        Menu m = menu;
        attributes.addFlashAttribute("menu", menu);
        service.save(menu);
        RedirectView reMav= new RedirectView("/presupuesto/crear");
        return reMav;
    }
}
