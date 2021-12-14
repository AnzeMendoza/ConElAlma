package com.gylgroup.conelalma.services;

import com.gylgroup.conelalma.entities.Combos;
import com.gylgroup.conelalma.repositories.ComboRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ComboService {
    @Autowired
    private ComboRepository comboRepository;
        
    @Transactional
    public void save(Combos combo){
        comboRepository.save(combo);
    }
    
    @Transactional
    public void update(Combos combo){
        Optional<Combos> miCombo = comboRepository.findById(combo.getId());
        if(miCombo.isPresent()){
            Combos newCombo = miCombo.get();
            newCombo.setEstado(true);
            newCombo.setFoto(combo.getFoto());
            newCombo.setListaBebidas(combo.getListaBebidas());
            newCombo.setListaComida(combo.getListaComida());
            newCombo.setNombre(combo.getNombre());
            newCombo.setPrecioCombo(combo.getPrecioCombo());
            comboRepository.save(newCombo);
        }
    }
    
    @Transactional
    public List<Combos> findAll(){
        return comboRepository.findAll();
    }
    
    @Transactional
    public Combos findById(Integer id){
        return comboRepository.findById(id).get();
    }
    
    @Transactional
    public List<Combos> findAllAndEstado(){
        return comboRepository.findAllAndEstado();
    }
    
        
    @Transactional
    public void enable(Integer id){
        comboRepository.enable(id);
    }
    
    @Transactional
    public void disable(Integer id){
        comboRepository.disable(id);
    }
    
    
    
    
    
    
    
    
}
