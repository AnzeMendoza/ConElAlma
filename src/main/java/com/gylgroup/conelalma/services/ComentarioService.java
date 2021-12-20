package com.gylgroup.conelalma.services;


import com.gylgroup.conelalma.entities.Comentario;
import com.gylgroup.conelalma.repositories.ComentarioRepository;
import com.gylgroup.conelalma.repositories.ReservaRepository;
import com.gylgroup.conelalma.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ComentarioService {

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    //FALTA VALIDAR QUE EXISTA EL USUARIO POR ID
    @Transactional
    public void save(Comentario dto ) throws Exception{
        if(dto.getDescripcion().trim()==null || dto.getDescripcion().trim().length()<10){
            throw new Exception("La descripcion no puede estar vacia o tener menos de 10 caracteres");
        }
        if(comentarioRepository.existsComentarioById(dto.getId())){
            throw new Exception("Ya existe un comentario asociado al ID: "+dto.getId());
        }

        Comentario comentario = new Comentario();

        comentario.setCalificacion(dto.getCalificacion());
        comentario.setDescripcion(dto.getDescripcion());
        comentario.setUsuario(usuarioRepository.findById(dto.getUsuario().getId()).orElse(null));
        comentario.setReserva(reservaRepository.findById(dto.getReserva().getId()).orElse(null));
        comentario.setEstado(true);

        comentarioRepository.save(comentario);
    }

    @Transactional
    public void update(Comentario dto) throws Exception{
        if(dto.getDescripcion().trim()==null || dto.getDescripcion().trim().length()<10){
            throw new Exception("La descripcion no puede estar vacia o tener menos de 10 caracteres");
        }
        if(!comentarioRepository.existsComentarioById(dto.getId())){
            throw new Exception("No existe un comentario asociado al ID: "+dto.getId());
        }

        if(!reservaRepository.existsById(dto.getReserva().getId())){
            throw new Exception("No existe un comentario asociado a la reserva con ID : "+dto.getReserva().getId());
        }
        //AGREGAR EXCEPCION
        Comentario comentario = comentarioRepository.findById(dto.getId()).orElse(null);

        comentario.setCalificacion(dto.getCalificacion());
        comentario.setDescripcion(dto.getDescripcion());
        comentario.setReserva(dto.getReserva());//preguntar
        comentario.setEstado(dto.getEstado());
        comentarioRepository.save(comentario);
    }

    @Transactional
    public Comentario findById(Integer id){
        Optional<Comentario> optional = comentarioRepository.findById(id);
        return optional.orElse(null);
    }

    @Transactional
    public void disable(Integer id){
        comentarioRepository.disable(id);
    }

    @Transactional
    public void enable(Integer id){
        comentarioRepository.enable(id);
    }

    @Transactional
    public List<Comentario> traerTodos(){
        return comentarioRepository.findAll();
    }

    @Transactional
    public List<Comentario> traerAltas(){
        return comentarioRepository.findAllEnable();
    }


    @Transactional
    public List<Comentario> traerPorReserva(Comentario dto) throws Exception{

        if(!comentarioRepository.existsComentarioById(dto.getId())){
            throw new Exception("No existe un comentario asociado al ID: "+dto.getId());
        }

        if(!reservaRepository.existsById(dto.getReserva().getId())){
            throw new Exception("No existe un comentario asociado a la reserva con ID : "+dto.getReserva().getId());
        }

        return comentarioRepository.findByReserva(dto.getReserva().getId());
    }

    /*-----------VALIDAR QUE EXISTAN USUARIO POR ID----------------*/
    @Transactional
    public List<Comentario> findByUsuarioId(Integer idUsuario){
        return comentarioRepository.findByUsuarioId(idUsuario);
    }



}
