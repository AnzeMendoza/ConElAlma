package com.gylgroup.conelalma.services;

import com.gylgroup.conelalma.entities.Menu;
import com.gylgroup.conelalma.repositories.ComboRepository;
import com.gylgroup.conelalma.repositories.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MenuService {
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private ComboRepository comboRepository;
    
    @Transactional
    public void save(Menu menu){
        menuRepository.save(menu);
    }
    
    @Transactional
    public void update(Menu menu){
        Optional<Menu> respuesta = menuRepository.findById(menu.getId());
        if(respuesta.isPresent()){
            Menu newMenu = respuesta.get();
            newMenu.setCantidadBaseComensales(menu.getCantidadBaseComensales());
            newMenu.setFoto(menu.getFoto());
            newMenu.setListaCombos(menu.getListaCombos());
            newMenu.setPrecioMenu(menu.getPrecioMenu());
            newMenu.setTipoEvento(menu.getTipoEvento());
            menuRepository.save(newMenu);
        }
    }
    
    @Transactional
    public List<Menu> findAll(){
        return menuRepository.findAll();
    }
    
    @Transactional
    public Menu findById(Integer Id){
        Optional <Menu> menu = menuRepository.findById(Id);
        return menu.orElse(null) ;
    }

    
}
