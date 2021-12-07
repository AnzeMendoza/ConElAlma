package com.gylgroup.conelalma.services;

import java.util.List;
import java.util.Optional;

import com.gylgroup.conelalma.entities.Rol;
import com.gylgroup.conelalma.exception.ExceptionService;
import com.gylgroup.conelalma.repositories.RolRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RolService {

    @Autowired
    RolRepository rolRepository;

    @Transactional(readOnly = true)
    public List<Rol> findAll() {
        return rolRepository.findAll();
    }

    @Transactional
    public void save(Rol rol) throws ExceptionService {

        Optional<Rol> opRol = rolRepository.findByNombre(rol.getNombre());
        if (opRol.isPresent()) {

            throw new ExceptionService("YA EXISTE UN ROL CON EL NOMBRE INDICADO!");
        } else {

            rol.setEstado(true);
            rolRepository.save(rol);
        }

    }

    public void update(Integer id, Rol rol) throws ExceptionService {

        Optional<Rol> opRol = rolRepository.findById(id);
        if (opRol.isPresent()) {

            Rol upRol = opRol.get();
            upRol.setNombre(rol.getNombre());
            upRol.setEstado(true);
            rolRepository.save(upRol);
        }
    }

    @Transactional
    public void disable(Integer id) {

        Optional<Rol> opRol = rolRepository.findById(id);
        if (opRol.isPresent()) {

            Rol rol = opRol.get();
            rol.setEstado(false);
            rolRepository.save(rol);
        }

    }

    @Transactional
    public void enable(Integer id) {

        Optional<Rol> opRol = rolRepository.findById(id);
        if (opRol.isPresent()) {

            Rol rol = opRol.get();
            rol.setEstado(true);
            rolRepository.save(rol);
        }

    }

}
