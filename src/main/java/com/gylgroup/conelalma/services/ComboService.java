package com.gylgroup.conelalma.services;

import com.gylgroup.conelalma.entities.Combo;
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
    public void save(Combo combo){
        comboRepository.save(combo);
    }
    
    @Transactional
    public void update(Combo combo){
        Optional<Combo> miCombo = comboRepository.findById(combo.getId());
        if(miCombo.isPresent()){
            Combo newCombo = miCombo.get();
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
    public List<Combo> findAll(){
        return comboRepository.findAll();
    }
    
    @Transactional
    public Combo findById(Integer id){
        return comboRepository.findById(id).get();
    }
    
    @Transactional
    public List<Combo> findAllAndEstado(){
        return comboRepository.findAllByEstado();
    }

    @Transactional
    public void enable(Integer id){
        changeStatus(id, true);
    }
    
    @Transactional
    public void disable(Integer id){
        changeStatus(id, false);
    }

    private void changeStatus(Integer id, boolean status) {
        Combo combo = comboRepository.getById(id);
        combo.setEstado(status);
        comboRepository.save(combo);
    }
}
