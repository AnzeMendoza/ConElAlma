package com.gylgroup.conelalma.services;

import com.gylgroup.conelalma.entities.Menu;
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

    @Transactional
    public void crearMenu(Menu dto) throws Exception{

        if(dto.getTipoEvento() ==null ){
            throw new Exception("El tipo de evento no puede ser null");
        }
        if(dto.getComboMenu().trim()==null ){
            throw new Exception("El combo de menu no puede estar vacío");
        }
        if(dto.getCantidadBaseComensales()==null || dto.getCantidadBaseComensales()<=0 ){
            throw new Exception("La cantidad de comensales debe ser mayor a 0");
        }
        if(dto.getPrecioBase()==null || dto.getPrecioBase()<=0 ){
            throw new Exception("La cantidad de comensales debe ser mayor a 0");
        }
        if( dto.getFoto().equals(" ") ){
            throw new Exception("No existe una foto");
        }
        if(!repository.existsMenuBy(dto.getId())){
            throw new Exception("No existe un menu asociado al ID: "+dto.getId());
        }

        Menu menu = new Menu();

        menu.setTipoEvento(dto.getTipoEvento());
        menu.setComboMenu(dto.getComboMenu());
        menu.setCantidadBaseComensales(dto.getCantidadBaseComensales());
        menu.setPrecioBase(dto.getPrecioBase());
        menu.setFoto(dto.getFoto());

        repository.save(menu);
    }
    @Transactional
    public void modificarMenu(Menu dto) throws Exception{

        if(dto.getTipoEvento() ==null ){
            throw new Exception("El tipo de evento no puede ser null");
        }
        if(dto.getComboMenu().trim()==null ){
            throw new Exception("El combo de menu no puede estar vacío");
        }
        if(dto.getCantidadBaseComensales()==null || dto.getCantidadBaseComensales()<=0 ){
            throw new Exception("La cantidad de comensales debe ser mayor a 0");
        }
        if(dto.getPrecioBase()==null || dto.getPrecioBase()<=0 ){
            throw new Exception("La cantidad de comensales debe ser mayor a 0");
        }
        if( dto.getFoto().equals(" ") ){
            throw new Exception("No existe una foto");
        }
        if(!repository.existsMenuBy(dto.getId())){
            throw new Exception("No existe un menu asociado al ID: "+dto.getId());
        }

        Menu menu = repository.findById(dto.getId()).get();

        menu.setTipoEvento(dto.getTipoEvento());
        menu.setComboMenu(dto.getComboMenu());
        menu.setPrecioBase(dto.getPrecioBase());
        menu.setCantidadBaseComensales(dto.getCantidadBaseComensales());
        menu.setFoto(dto.getFoto());

        repository.save(menu);
    }

    @Transactional(readOnly=true)
    public Menu obtenerMenuXId(Integer id){
        Optional<Menu> optional = repository.findById(id);
        return optional.orElse(null);
    }

    @Transactional
    public void bajaMenu(Integer id) throws Exception{
        if(!repository.existsMenuBy(id)){
            throw new Exception("No existe un menu asociado al ID: "+id + " para la baja");
        }
        repository.deleteById(id);
    }

    @Transactional
    public void altaMenu(Integer id) throws Exception{
        if(repository.existsMenuBy(id)){
            throw new Exception("Ya existe un menu asociado al ID: "+id + " para la baja");
        }
        repository.darAltaMenu(id);
    }

    @Transactional(readOnly = true)
    public List<Menu> obtenerListaMenus() throws Exception{
        if (repository.findAll().isEmpty()){
            throw new Exception("La lista de menus esta vacía");
        }
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Menu> obtenerAltas() throws Exception{
        if (repository.obtenerAltas().isEmpty()){
            throw new Exception("La lista de menus de alta esta vacía");
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
