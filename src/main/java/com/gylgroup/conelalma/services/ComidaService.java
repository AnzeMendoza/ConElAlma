package com.gylgroup.conelalma.services;

import com.gylgroup.conelalma.entities.Comida;
import com.gylgroup.conelalma.repositories.ComidaRepository;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComidaService {

    @Autowired
    private ComidaRepository comidaRepository;
    
    @Transactional
    public void save(Comida miComida){
        Comida comida = new Comida();
        comida.setEstado(true);
        comida.setNombre(miComida.getNombre());
        comida.setPrecioUnitario(miComida.getPrecioUnitario());
        comidaRepository.save(comida);
    }
    
    @Transactional
    public void update(Comida miComida){
        
        if(comidaRepository.existsById(miComida.getId())){
            Comida updateComida = comidaRepository.findById(miComida.getId()).get();
            updateComida.setNombre(miComida.getNombre());
            updateComida.setPrecioUnitario(miComida.getPrecioUnitario());
            comidaRepository.save(updateComida);
        }
    }
    
    @Transactional
    public List<Comida> findAll(){
        return comidaRepository.findAll();
    }
    
    @Transactional
    public List<Comida> findAllAndEstado(){
        return comidaRepository.findAllAndEstado();
    }
    
    
    @Transactional
    public void disable(Integer id) {
        comidaRepository.disable(id);
    }
    
    @Transactional
    public void enable(Integer id) {
        comidaRepository.enable(id);
    }
    
    @Transactional
    public boolean existsById(Integer id){
        return comidaRepository.existsById(id);
    }
    
    @Transactional
    public Comida findById(Integer id){
        return comidaRepository.findById(id).get();
    }
    
    
    
}
