package com.gylgroup.conelalma.services;

import com.gylgroup.conelalma.entities.Cupon;
import com.gylgroup.conelalma.repositories.CuponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CuponService {

    @Autowired
    private CuponRepository cuponRepository;

    @Transactional
    public void save(Cupon cupon) {
        cuponRepository.save(cupon);
    }

    @Transactional
    public void update(Integer id, Cupon cupon) throws Exception {
        Optional<Cupon> respuesta = Optional.ofNullable(findById(id));
        if (!respuesta.isPresent()) {
            throw new Exception("No se pudo encontrar el cupon para modificar");
        }
        Cupon opcupon = respuesta.get();
        opcupon.setCodigo(cupon.getCodigo());
        opcupon.setDescuento(cupon.getDescuento());
        opcupon.setFechaModificacion(new Date());
        cuponRepository.save(opcupon);
    }

    @Transactional
    public List<Cupon> findAll() {
        return cuponRepository.findAll();
    }

    @Transactional
    public List<Cupon> findAllAndEstado(boolean b) {
        return (List<Cupon>) cuponRepository.findAllByEstado(Boolean.TRUE);
    }

    @Transactional
    public Cupon findById(Integer id) throws Exception {
        return cuponRepository.findById(id).get();
    }

    @Transactional
    public boolean existsById(Integer id){
        return cuponRepository.existsById(id);
    }

    @Transactional
    public void enable(Integer id) {
        changeStatus(id, true);
    }

    @Transactional
    public void disable(Integer id) {
        changeStatus(id, false);
    }

    private void changeStatus(Integer id, boolean status) {
        Cupon cupon = cuponRepository.getById(id);
        cupon.setEstado(status);
        cuponRepository.save(cupon);
    }
}
