package com.gylgroup.conelalma.services;

import com.gylgroup.conelalma.entities.Comida;
import com.gylgroup.conelalma.repositories.ComidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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
    public void disable(Integer id) {
        changeStatus(id, false);
    }
    
    @Transactional
    public void enable(Integer id) {
        changeStatus(id, true);
    }
    
    @Transactional
    public boolean existsById(Integer id){
        return comidaRepository.existsById(id);
    }
    
    @Transactional
    public Comida findById(Integer id){
        return comidaRepository.findById(id).get();
    }

    @Transactional
    public List<Comida> findAllByEstado(){
        return comidaRepository.findAllByEstado();
    }

    private void changeStatus(Integer id, boolean status) {
        Comida comida = comidaRepository.getById(id);
        comida.setEstado(status);
        comidaRepository.save(comida);
    }
}
