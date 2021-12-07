package com.gylgroup.conelalma.services;

import com.gylgroup.conelalma.entities.Bebida;
import com.gylgroup.conelalma.entities.Menu;
import com.gylgroup.conelalma.repositories.BebidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class BebidaService {

    @Autowired
    private BebidaRepository repository;

    @Transactional
    public void crearBebida(Bebida dto) throws Exception{

        if(dto.getTipoEvento() ==null ){
            throw new Exception("El tipo de evento no puede ser null");
        }
        if(dto.getComboBebida().trim()==null ){
            throw new Exception("El combo de bebida no puede estar vacío");
        }
        if(dto.getCantidadBaseComensales()==null || dto.getCantidadBaseComensales()<=0 ){
            throw new Exception("La cantidad de comensales debe ser mayor a 0");
        }
        if(dto.getPrecioBase()==null || dto.getPrecioBase()<=0 ){
            throw new Exception("El precio base debe ser mayor a 0");
        }
        if( dto.getFoto().equals(" ") ){
            throw new Exception("No existe una foto");
        }
        if(!repository.existsBebidaBy(dto.getId())){
            throw new Exception("No existe una Bebida asociada al ID: "+dto.getId());
        }

        Bebida bebida = new Bebida();

        bebida.setTipoEvento(dto.getTipoEvento());
        bebida.setComboBebida(dto.getComboBebida());
        bebida.setCantidadBaseComensales(dto.getCantidadBaseComensales());
        bebida.setPrecioBase(dto.getPrecioBase());
        bebida.setFoto(dto.getFoto());

        repository.save(bebida);
    }
    @Transactional
    public void modificarBebida(Bebida dto) throws Exception{

        if(dto.getTipoEvento() ==null ){
            throw new Exception("El tipo de evento no puede ser null");
        }
        if(dto.getComboBebida().trim()==null ){
            throw new Exception("El combo de bebida no puede estar vacío");
        }
        if(dto.getCantidadBaseComensales()==null || dto.getCantidadBaseComensales()<=0 ){
            throw new Exception("La cantidad de comensales debe ser mayor a 0");
        }
        if(dto.getPrecioBase()==null || dto.getPrecioBase()<=0 ){
            throw new Exception("El precio base debe ser mayor a 0");
        }
        if( dto.getFoto().equals(" ") ){
            throw new Exception("No existe una foto");
        }
        if(!repository.existsBebidaBy(dto.getId())){
            throw new Exception("No existe una Bebida asociada al ID: "+dto.getId());
        }

        Bebida bebida = repository.findById(dto.getId()).get();

        bebida.setTipoEvento(dto.getTipoEvento());
        bebida.setComboBebida(dto.getComboBebida());
        bebida.setPrecioBase(dto.getPrecioBase());
        bebida.setCantidadBaseComensales(dto.getCantidadBaseComensales());
        bebida.setFoto(dto.getFoto());

        repository.save(bebida);
    }

    @Transactional(readOnly=true)
    public Bebida obtenerBebidaXId(Integer id){
        Optional<Bebida> optional = repository.findById(id);
        return optional.orElse(null);
    }

    @Transactional
    public void bajaBebida(Integer id)throws Exception{
        if(!repository.existsBebidaBy(id)){
            throw new Exception("No existe una bebida asociada al ID: "+id + " para la baja");
        }
        repository.deleteById(id);
    }

    @Transactional
    public void altaBebida(Integer id)throws Exception{
        if(repository.existsBebidaBy(id)){
            throw new Exception("Ya existe una bebida asociada al ID: "+id + " para la baja");
        }
        repository.darAltaBebida(id);
    }

    @Transactional(readOnly = true)
    public List<Bebida> obtenerListaBebidas()throws Exception{
        if (repository.findAll().isEmpty()){
            throw new Exception("La lista de bebidas esta vacía");
        }
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Menu> obtenerAltas() throws Exception{
        if (repository.obtenerAltas().isEmpty()){
            throw new Exception("La lista de bebidas de alta esta vacía");
        }
        return repository.obtenerAltas();
    }

    @Transactional
    public List<Menu> obtenerMenuXComensales(Integer comensales) throws Exception{
        if (comensales <= 0 ){
            throw new Exception("La cantidad de comensales debe ser mayor a 0");
        }
        return repository.listaMenuXComensales(comensales);
    }


}
