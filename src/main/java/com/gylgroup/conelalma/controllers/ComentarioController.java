package com.gylgroup.conelalma.controllers;


import com.gylgroup.conelalma.entities.Comentario;
import com.gylgroup.conelalma.services.ComentarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/comentarios")
public class ComentarioController {

    @Autowired
    private ComentarioService comentarioService;

    @GetMapping
    public ModelAndView listaComentarios(){
        ModelAndView mav = new ModelAndView("lista-comentarios");
        //traer comentarios solo en alta?
        mav.addObject("listaComentarios",comentarioService.traerTodos());
        return mav;
    }

    @GetMapping("/crear")
    public ModelAndView crearComentario(){
        ModelAndView mav = new ModelAndView("comentario-formulario");

        mav.addObject("comentario",new Comentario());
        mav.addObject("action","guardar");
        mav.addObject("title","Ingresar nuevo comentario");

        return mav;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editarComentario(@PathVariable Integer id){
        ModelAndView mav = new ModelAndView("comentario-formulario");

        mav.addObject("comentario",comentarioService.buscarPorId(id));
        mav.addObject("action","modifcar");
        mav.addObject("title","Editar comentario");

        return mav;
    }

    @PostMapping("/guardar")
    public RedirectView persistirComentario(@ModelAttribute Comentario comentario, RedirectAttributes attributes){

        try {
            comentarioService.crearComentario(comentario);
            attributes.addFlashAttribute("exito","Comentario registrado con exito.");
            return new RedirectView("/comentarios");
        } catch (Exception e) {
            attributes.addFlashAttribute("comentario",comentario);
            attributes.addFlashAttribute("error-name",e.getMessage());
            return new RedirectView("/comentario/crear");
        }
    }

    @PostMapping("/modificar")
    public RedirectView modifcarComentario(@ModelAttribute Comentario comentario,RedirectAttributes attributes){
        RedirectView reMav = new RedirectView("/comentarios");

        try {
            comentarioService.modificarComentario(comentario);
            attributes.addFlashAttribute("exito", "Modificacion exitosa");
        } catch (Exception e) {
            attributes.addFlashAttribute("comentario",comentario);
            attributes.addFlashAttribute("error-name",e.getMessage());
            reMav.setUrl("/comentarios/editar/"+comentario.getId());
        }
        return reMav;
    }

    @PostMapping("/alta/{id}")
    public RedirectView altaComentario(@PathVariable Integer id){
        RedirectView reMav = new RedirectView("/comentarios");
        comentarioService.altaComentario(id);
        return reMav;
    }

    @PostMapping("/baja/{id}")
    public RedirectView bajaComentario(@PathVariable Integer id){
        RedirectView reMav = new RedirectView("/comentarios");
        comentarioService.bajaComentario(id);
        return reMav;
    }


}
