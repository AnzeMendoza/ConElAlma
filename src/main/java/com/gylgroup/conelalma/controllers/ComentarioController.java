package com.gylgroup.conelalma.controllers;

import com.gylgroup.conelalma.entities.Comentario;
import com.gylgroup.conelalma.repositories.ReservaRepository;
import com.gylgroup.conelalma.repositories.UsuarioRepository;
import com.gylgroup.conelalma.services.ComentarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/comentario")
public class ComentarioController {

    @Autowired
    private ComentarioService comentarioService;

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    /* @GetMapping
     public ModelAndView listaComentarios(){
         ModelAndView mav = new ModelAndView("comentarios");
         //traer comentarios solo en alta?
         mav.addObject("comentarios",comentarioService.traerTodos());
         return mav;
     }*/
    @GetMapping
    public ModelAndView save(){
        ModelAndView mav = new ModelAndView("public/comentario-formulario");
        Comentario comentario = new Comentario();
        comentario.setUsuario(usuarioRepository.findById(2).orElse(null));
        comentario.setReserva(reservaRepository.findById(1).orElse(null));
        mav.addObject("comentario",comentario);
        //mav.addObject("usuario", usuarioRepository.getById(2));
        mav.addObject("action","guardar");
        mav.addObject("title","Ingresar nuevo comentario");

        return mav;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editarComentario(@PathVariable Integer id){
        ModelAndView mav = new ModelAndView("public/comentario-formulario");
        mav.addObject("comentario",comentarioService.findById(id));
        mav.addObject("action","modificar");
        mav.addObject("title","Editar comentario");

        return mav;
    }

    @PostMapping("/guardar")
    public RedirectView persistirComentario(@ModelAttribute("comentario") Comentario comentario, RedirectAttributes attributes){
        try {
            comentarioService.save(comentario);
            attributes.addFlashAttribute("exito","Comentario registrado con exito.");
            return new RedirectView("/");
        } catch (Exception e) {
            attributes.addFlashAttribute("comentario",comentario);
            attributes.addFlashAttribute("error-name",e.getMessage());
            return new RedirectView("/comentario");
        }
    }

    @PostMapping("/modificar")
    public RedirectView modifcarComentario(@ModelAttribute("comentario") Comentario comentario,RedirectAttributes attributes){
        RedirectView reMav = new RedirectView();

        try {
            comentarioService.update(comentario);
            attributes.addFlashAttribute("exito", "Modificacion exitosa");
            reMav.setUrl("/");
        } catch (Exception e) {
            attributes.addFlashAttribute("comentario",comentario);
            attributes.addFlashAttribute("error-name",e.getMessage());
            reMav.setUrl("/comentario/editar/"+comentario.getId());
        }
        return reMav;
    }

    @PostMapping("/alta/{id}")
    public RedirectView enable(@PathVariable Integer id){
        RedirectView reMav = new RedirectView("/comentario");
        comentarioService.enable(id);
        return reMav;
    }

    @PostMapping("/baja/{id}")
    public RedirectView disable(@PathVariable Integer id){
        RedirectView reMav = new RedirectView("/comentario");
        comentarioService.disable(id);
        return reMav;
    }


}
