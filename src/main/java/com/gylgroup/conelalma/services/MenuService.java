package com.gylgroup.conelalma.services;

import com.gylgroup.conelalma.entities.Menu;
import com.gylgroup.conelalma.exception.ExceptionService;
import com.gylgroup.conelalma.repositories.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MenuService {

    @Autowired
    private MenuRepository repository;


    public void save(Menu menu) throws ExceptionService{

       /* if(repository.findMenuByNombre(menu.getNombre()).isPresent()){
            throw new ExceptionService("Ya existe un menú con el mismo nombre");
        }*/
        menu.setEstado(true);
        repository.save(menu);

    }

    @Transactional
    public void update(Integer id,Menu menu) throws ExceptionService{

        Optional<Menu> opMenu = repository.findById(id);

        if(opMenu.isPresent() && opMenu.get().getEstado().equals(true)){

            Menu menuNew = new Menu();

           // menuNew.setNombre(menu.getNombre());
            menuNew.setTipoEvento(menu.getTipoEvento());
            menuNew.setCantidadBaseComensales(menu.getCantidadBaseComensales());
            //menuNew.setPrecioMenu(menu.getPrecioMenu());
            menuNew.setEstado(menu.getEstado());
            menuNew.setListaCombos(menu.getListaCombos());
            menuNew.setFoto(menu.getFoto());


            repository.save(menuNew);
        } else{
            throw new ExceptionService("No existe el menu seleccionado");
        }

    }

    @Transactional(readOnly = true)
    public Menu findById(Integer id){
        return repository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Menu> findAll(){
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Menu> findAllByEstado() throws ExceptionService {

        List<Menu> listaMenu = repository.findAllByEstado();

        if (listaMenu.isEmpty()){
            throw new ExceptionService("No existen menus en alta");

        }else{
            return listaMenu;
        }

    }

    @Transactional
    public void disable(Integer id){
        Optional<Menu> opMenu = repository.findById(id);

        if (opMenu.isPresent()){

            Menu menu = opMenu.get();
            menu.setEstado(false);
            repository.save(menu);
        }
    }

    @Transactional
    public void enable(Integer id){

        Optional<Menu> opMenu = repository.findById(id);

        if (opMenu.isPresent()){

            Menu menu = opMenu.get();
            menu.setEstado(true);
            repository.save(menu);
        }
    }

    public Double calculoPrecioBase(Integer cantPers, Double precioCombo){
        return cantPers*precioCombo;
    }

}
