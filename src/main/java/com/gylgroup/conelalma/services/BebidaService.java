package com.gylgroup.conelalma.services;

import com.gylgroup.conelalma.entities.Bebida;
import com.gylgroup.conelalma.exception.ExceptionService;
import com.gylgroup.conelalma.repositories.BebidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BebidaService {

    @Autowired
    private BebidaRepository repository;

    @Transactional
    public void save(Bebida bebida) throws ExceptionService{

        if(repository.findBebidaByNombre(bebida.getNombre()).isPresent()){
            throw new ExceptionService("Ya existe una bebida con el mismo nombre");
        }
        bebida.setEstado(true);
        repository.save(bebida);
    }

    @Transactional
    public void update(Integer id,Bebida bebida) throws ExceptionService{
        Optional<Bebida> opBebida = repository.findById(id);

        if(!opBebida.isPresent()){
            throw new ExceptionService("No existe esta bebida");
        }

        Bebida bebidaNew = opBebida.get();
        bebidaNew.setNombre(bebida.getNombre());
        bebidaNew.setEstado(bebida.getEstado());
        bebidaNew.setPrecioUnitario(bebida.getPrecioUnitario());
        repository.save(bebidaNew);
    }

    @Transactional(readOnly = true)
    public List<Bebida> findAll(){
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Bebida findById(Integer id){
        return repository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Bebida> findAllByEstado() throws ExceptionService{
        List<Bebida> listaBebida = repository.findAllByEstado();

        if (listaBebida.isEmpty()){
            throw new ExceptionService("No existen bebidas en alta");
        }else{
            return listaBebida;
        }
    }

    @Transactional
    public void disable(Integer id){
        Optional<Bebida> opBebida = repository.findById(id);

        if (opBebida.isPresent()){
            Bebida bebida = opBebida.get();
            bebida.setEstado(false);
            repository.save(bebida);
        }
    }

    @Transactional
    public void enable(Integer id){
        Optional<Bebida> opBebida = repository.findById(id);
        if (opBebida.isPresent()){
            Bebida bebida = opBebida.get();
            bebida.setEstado(true);
            repository.save(bebida);
        }
    }

    @Transactional
    public boolean existsById(Integer id){
        return repository.existsById(id);
    }
}

