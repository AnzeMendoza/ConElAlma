package com.gylgroup.conelalma.controllers;

import com.gylgroup.conelalma.entities.Comentario;
import com.gylgroup.conelalma.entities.Reserva;
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
import java.util.List;
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
    @PreAuthorize("hasAnyRole('CLIENTE')")
    public ModelAndView save(HttpSession session, HttpServletRequest request) throws Exception {//AGREGAR FLASHMAP para exito/error en html
        ModelAndView mav = new ModelAndView("public/comentario-formulario");
        Comentario comentario = new Comentario();
        mav=cargarObjetos(session,request,comentario,mav);
        return mav;
    }

    @GetMapping("/editar/{id}")
    @PreAuthorize("hasAnyRole('CLIENTE')")
    public ModelAndView editarComentario(@PathVariable Integer id){
        ModelAndView mav = new ModelAndView("public/comentario-formulario");
        mav.addObject("comentario",comentarioService.findById(id));
        mav.addObject("action","modificar");
        mav.addObject("title","Editar comentario");

        return mav;
    }

    @PostMapping("/guardar")
    @PreAuthorize("hasAnyRole('CLIENTE')")
    public RedirectView persistirComentario(@ModelAttribute("comentario") Comentario comentario, RedirectAttributes attributes){
        try {
            comentarioService.save(comentario);
            attributes.addFlashAttribute("exito","Comentario registrado con exito.");
            RedirectView reMav = new RedirectView();
            reMav.setUrl("/comentario/miscomentarios");
            return reMav;
        } catch (Exception e) {
            attributes.addFlashAttribute("comentario",comentario);
            attributes.addFlashAttribute("error-name",e.getMessage());
            return new RedirectView("/comentario");
        }
    }

    @PostMapping("/modificar")
    @PreAuthorize("hasAnyRole('CLIENTE')")
    public RedirectView modifcarComentario(@ModelAttribute("comentario") Comentario comentario,RedirectAttributes attributes){
        RedirectView reMav = new RedirectView();

        try {
            comentarioService.update(comentario);
            attributes.addFlashAttribute("exito", "Modificacion exitosa");
            reMav.setUrl("/reservas");
        } catch (Exception e) {
            attributes.addFlashAttribute("comentario",comentario);
            attributes.addFlashAttribute("error-name",e.getMessage());
            reMav.setUrl("/comentario/editar/"+comentario.getId());
            System.err.println(e.getMessage());
        }
        return reMav;
    }

    @PostMapping("/alta/{id}")
    @PreAuthorize("hasAnyRole('CLIENTE','ADMIN')")
    public RedirectView enable(@PathVariable Integer id,HttpSession session){
        RedirectView reMav = new RedirectView("");
        Usuario user = (Usuario) session.getAttribute("user");

        if(user.getRol().getId()==1){
            reMav.setUrl("/");
        }else{
            reMav.setUrl("/comentario/todos");

        }

        comentarioService.enable(id);
        return reMav;
    }

    @PostMapping("/baja/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','CLIENTE')")
    public RedirectView disable(@PathVariable Integer id,HttpSession session){
        RedirectView reMav = new RedirectView("");
        Usuario user = (Usuario) session.getAttribute("user");

        if(user.getRol().getId()==1){
            reMav.setUrl("/");
        }else{
            reMav.setUrl("/comentario/todos");

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
    @PreAuthorize("hasAnyRole('CLIENTE')")
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
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ModelAndView listaComentarios() {
        ModelAndView mav = new ModelAndView("admin/comentarios");
        mav.addObject("comentarios", comentarioService.findAll());
        return mav;
    }

}
