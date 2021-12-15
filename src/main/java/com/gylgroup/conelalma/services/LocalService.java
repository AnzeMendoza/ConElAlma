package com.gylgroup.conelalma.services;

import com.gylgroup.conelalma.entities.Local;
import com.gylgroup.conelalma.repositories.LocalRepository;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocalService {

    @Autowired
    private LocalRepository localRepository;
    
    @Transactional
    public void save(Local miLocal){
        Local local = new Local();
        local.setNombre(miLocal.getNombre());
        local.setCantidadMaximaPersonas(miLocal.getCantidadMaximaPersonas());
        local.setDireccion(miLocal.getDireccion());
        local.setFoto(miLocal.getFoto());
        local.setPrecio(miLocal.getPrecio());
        local.setEstado(true);
        localRepository.save(local);
    }
    
    @Transactional
    public void update(Local miLocal){
        if(localRepository.existsById(miLocal.getId())){
            Local local = localRepository.BuscarLocalPorId(miLocal.getId());
            local.setNombre(miLocal.getNombre());
            local.setCantidadMaximaPersonas(miLocal.getCantidadMaximaPersonas());
            local.setDireccion(miLocal.getDireccion());
            local.setFoto(miLocal.getFoto());
            local.setPrecio(miLocal.getPrecio());
            local.setEstado(true);
            localRepository.save(local);
        }  
    }
    
    @Transactional
    public List<Local> findAll(){
        return localRepository.misLocales();
    }
    
    @Transactional
    public List<Local> findAllAndEstado(){
        return localRepository.findAllAndEstado();
    }
    
    @Transactional
    public Local findById(Integer id){
       return localRepository.BuscarLocalPorId(id);
    }
    
    @Transactional
    public List<Local> filtrarPorPrecioMax(Double precio){
        return localRepository.BuscarPorPrecioMaximo(precio);
    }
    
    @Transactional
    public List<Local> filtrarPorCapacidad(Integer capacidad){
        return localRepository.BuscarPorCantidadPersonas(capacidad);
    }
    
    @Transactional
    public List<Local> filtrarPorUbicacion(String ubicacion){
        return localRepository.BuscarPorDireccion(ubicacion);
    }
    
    @Transactional
    public boolean existsLocal(Integer id){
        return localRepository.existsById(id);
    }
    
    @Transactional
    public void disable(Integer id) {
        localRepository.disable(id);
    }
    
    @Transactional
    public void enable(Integer id) {
        localRepository.enable(id);
    }
}
