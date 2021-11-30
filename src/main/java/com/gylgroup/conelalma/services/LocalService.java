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
    public void crearLocal(Local miLocal){
        Local local = new Local();
        local.setCantidadMaximaPersonas(miLocal.getCantidadMaximaPersonas());
        local.setDireccion(miLocal.getDireccion());
        local.setFoto(miLocal.getFoto());
        local.setPrecio(miLocal.getPrecio());
        localRepository.save(local);
    }
    
    @Transactional
    public void modificarLocal(Local miLocal){
        if(localRepository.existsById(miLocal.getId())){
            Local local = new Local();
            local.setCantidadMaximaPersonas(miLocal.getCantidadMaximaPersonas());
            local.setDireccion(miLocal.getDireccion());
            local.setFoto(miLocal.getFoto());
            local.setPrecio(miLocal.getPrecio());
            localRepository.save(local);
        }  
    }
    
    @Transactional
    public List<Local> misLocales(){
        return localRepository.findAll();
    }
    
    @Transactional
    public Local BuscarLocalPorId(Integer id){
       return localRepository.BuscarLocalPorId(id);
    }
    
    @Transactional
    public void eliminarLocal(Integer id) {
        localRepository.deleteById(id);
    }
}
