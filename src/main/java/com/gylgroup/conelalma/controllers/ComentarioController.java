package com.gylgroup.conelalma.controllers;

import com.gylgroup.conelalma.entities.Comentario;
import com.gylgroup.conelalma.entities.Usuario;
import com.gylgroup.conelalma.repositories.ReservaRepository;
import com.gylgroup.conelalma.repositories.UsuarioRepository;
import com.gylgroup.conelalma.services.ComentarioService;
import com.gylgroup.conelalma.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/comentario")
public class ComentarioController {

    @Autowired
    private ComentarioService comentarioService;

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private UsuarioService usuarioService;

    /* @GetMapping
     public ModelAndView listaComentarios(){
         ModelAndView mav = new ModelAndView("comentarios");
         //traer comentarios solo en alta?
         mav.addObject("comentarios",comentarioService.findAll());
         return mav;
     }*/
    @GetMapping
    public ModelAndView save(HttpSession session, HttpServletRequest request){//AGREGAR FLASHMAP para exito/error en html
        ModelAndView mav = new ModelAndView("public/comentario-formulario");
        Comentario comentario = new Comentario();
        mav=cargarObjetos(session,request,comentario,mav);

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
            System.err.println(e.getMessage());
        }
        return reMav;
    }

    @PostMapping("/alta/{id}")
    public RedirectView enable(@PathVariable Integer id,HttpSession session){
        RedirectView reMav = new RedirectView("");
        Usuario user = (Usuario) session.getAttribute("user");

        if(user.getRol().getId()==1){
            reMav.setUrl("/");
        }else{
            reMav.setUrl("/admin");

        }

        comentarioService.enable(id);
        return reMav;
    }

    @PostMapping("/baja/{id}")
    public RedirectView disable(@PathVariable Integer id,HttpSession session){
        RedirectView reMav = new RedirectView("");
        Usuario user = (Usuario) session.getAttribute("user");

        if(user.getRol().getId()==1){
            reMav.setUrl("/");
        }else{
            reMav.setUrl("/admin");

        }
        comentarioService.disable(id);
        return reMav;
    }

    private ModelAndView cargarObjetos(HttpSession session, HttpServletRequest request,Comentario comentario, ModelAndView mav){
        Map<String,?> map = RequestContextUtils.getInputFlashMap(request);
        if(session.getAttribute("user")!=null){
            Usuario user = (Usuario) session.getAttribute("user");
            comentario.setUsuario(usuarioService.finById(user.getId()));
            mav.addObject("usuario",user);
            mav.addObject("logueado","true");
        }else{
            mav.addObject("logueado","false");
        }

        if(map!=null){
            mav.addObject("error",map.get("error-name"));
            mav.addObject("exito",map.get("exito"));
            mav.addObject("comentario",map.get("comentario"));
        }else{
            mav.addObject("comentario",comentario);
        }

        comentario.setReserva(reservaRepository.findById(1).orElse(null));
        mav.addObject("action","guardar");
        mav.addObject("title","Ingresar nuevo comentario");
        return mav;
    }

    @GetMapping("/miscomentarios")
    public ModelAndView misComentarios(HttpSession session, HttpServletRequest request){
        ModelAndView mav = new ModelAndView("public/comentario");
        Map<String,?> map = RequestContextUtils.getInputFlashMap(request);
        if(session.getAttribute("user")!=null){
            Usuario user = (Usuario) session.getAttribute("user");
            mav.addObject("usuario",user);
            mav.addObject("comentarios",comentarioService.findByUsuarioId(user.getId()));
            mav.addObject("logueado","true");
        }else{
            mav.addObject("logueado","false");
        }

        return mav;
    }

    @GetMapping("/todos")
    public ModelAndView listaComentarios() {
        ModelAndView mav = new ModelAndView("admin/comentarios");
        mav.addObject("comentarios", comentarioService.findAll());
        return mav;
    }

}
