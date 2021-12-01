package com.gylgroup.conelalma.services;


import com.gylgroup.conelalma.entities.Comentario;
import com.gylgroup.conelalma.repositories.ComentarioRepository;
import com.gylgroup.conelalma.repositories.ReservaRepository;
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
    private ReservaRepository reservaRepository;

    //FALTA VALIDAR QUE EXISTA EL USUARIO POR ID
    @Transactional
    public void crearComentario(Comentario dto ) throws Exception{
        if(dto.getDescripcion().trim()==null || dto.getDescripcion().trim().length()<10){
            throw new Exception("La descripcion no puede estar vacia o tener menos de 10 caracteres");
        }
        if(comentarioRepository.existsComentarioById(dto.getId())){
            throw new Exception("Ya existe un comentario asociado al ID: "+dto.getId());
        }

        Comentario comentario = new Comentario();

        comentario.setCalificacion(dto.getCalificacion());
        comentario.setDescripcion(dto.getDescripcion());
        comentario.setUsuario(dto.getUsuario());
        comentario.setReserva(dto.getReserva());
        comentario.setEstado(dto.getEstado());

        comentarioRepository.save(comentario);
    }

    @Transactional
    public void modificarComentario(Comentario dto) throws Exception{
        if(dto.getDescripcion().trim()==null || dto.getDescripcion().trim().length()<10){
            throw new Exception("La descripcion no puede estar vacia o tener menos de 10 caracteres");
        }
        if(!comentarioRepository.existsComentarioById(dto.getId())){
            throw new Exception("No existe un comentario asociado al ID: "+dto.getId());
        }

        if(!reservaRepository.existsReservaById(dto.getReserva().getId())){
            throw new Exception("No existe un comentario asociado a la reserva con ID : "+dto.getReserva().getId());
        }

        Comentario comentario = new Comentario();

        comentario.setCalificacion(dto.getCalificacion());
        comentario.setDescripcion(dto.getDescripcion());
        comentario.setReserva(dto.getReserva());//preguntar
        comentario.setEstado(dto.getEstado());

        comentarioRepository.save(comentario);

    }

    @Transactional
    public Comentario buscarPorId(Integer id){
        Optional<Comentario> optional = comentarioRepository.findById(id);
        return optional.orElse(null);
    }

    @Transactional
    public void bajaComentario(Integer id){
        comentarioRepository.deleteById(id);
    }

    @Transactional
    public void altaComentario(Integer id){
        comentarioRepository.habilitar(id);
    }

    @Transactional
    public List<Comentario> traerTodos(){
        return comentarioRepository.findAll();
    }

    @Transactional
    public List<Comentario> traerAltas(){
        return comentarioRepository.traerAltas();
    }


    @Transactional
    public List<Comentario> traerPorReserva(Comentario dto) throws Exception{

        if(!comentarioRepository.existsComentarioById(dto.getId())){
            throw new Exception("No existe un comentario asociado al ID: "+dto.getId());
        }

        if(!reservaRepository.existsReservaById(dto.getReserva().getId())){
            throw new Exception("No existe un comentario asociado a la reserva con ID : "+dto.getReserva().getId());
        }

        return comentarioRepository.traerPorReserva(dto.getReserva().getId());
    }

    /*-----------VALIDAR QUE EXISTAN USUARIO POR ID----------------*/
    @Transactional
    public List<Comentario> traerPorUsuario(Comentario dto){
        return comentarioRepository.traerPorUsuario(dto.getUsuario().getId());
    }



}
