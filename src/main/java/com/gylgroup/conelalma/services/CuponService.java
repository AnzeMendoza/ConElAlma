package com.gylgroup.conelalma.services;

import com.gylgroup.conelalma.entities.Cupon;
import com.gylgroup.conelalma.repositories.CuponRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CuponService {
/*    @Autowired
    private CuponRepository cuponRepository;
    

    @Transactional
    public void save(String nombre, String codigo, Integer descuento) {
        Cupon cupon = new Cupon();
        //LocalDateTime date = LocalDateTime.now();
        cupon.setCodigo(codigo);
        cupon.setDescuento(descuento);
        cupon.setEstado(Boolean.TRUE);
        //cupon.setFechaCreacion(date);
        
        cuponRepository.save(cupon);
    }

    @Transactional
    public void update(Integer id, String codigo, Integer descuento, Integer idUsuario) {
        Optional<Cupon> respuesta = cuponRepository.findById(id);
        if (respuesta.isPresent()) {
            Cupon cupon = respuesta.get();
            cupon.setCodigo(codigo);
            cupon.setDescuento(descuento);
            //cupon.setFechaModificacion(LocalDateTime.now());
            

            cuponRepository.save(cupon);
        }
    }

    @Transactional
    public List<Cupon> findAll() {
        List<Cupon> cuponList = cuponRepository.findAll();
        return cuponList;
    }

    @Transactional
    public Cupon findById(Integer id) {
        Optional<Cupon> respuesta = cuponRepository.findById(id);
        if (respuesta.isPresent()) {
            Cupon cupon = respuesta.get();
            return cupon;
        }
        return null;

    }

    @Transactional(readOnly = true)
    public Cupon findByNombre(String nombre) {
        Cupon cupon = cuponRepository.findByNombre(nombre).get();
        return cupon;
    }

    @Transactional(readOnly = true)
    public Cupon findByCodigo(String codigo) {
        Cupon cupon = cuponRepository.findByCodigo(codigo).get();
        return cupon;
    }

    @Transactional(readOnly = true)
    public Cupon findByDescuento(String descuento) {
        Cupon cupon = cuponRepository.findByDescuento(descuento).get();
        return cupon;
    }

    @Transactional
    public void enable(Integer id) {
        Optional<Cupon> respuesta = cuponRepository.findById(id);
        if (respuesta.isPresent()) {
            Cupon cupon = respuesta.get();
            cupon.setEstado(Boolean.TRUE);

            cuponRepository.save(cupon);
        }
    }

    @Transactional
    public void disable(Integer id) {
        Optional<Cupon> respuesta = cuponRepository.findById(id);
        if (respuesta.isPresent()) {
            Cupon cupon = respuesta.get();
            cupon.setEstado(Boolean.FALSE);

            cuponRepository.save(cupon);
        }
    }
    
    @Transactional
    public void cuponDelete(Integer id){
        Optional<Cupon> respuesta = cuponRepository.findById(id);
        if (respuesta.isPresent()){
            Cupon cupon = respuesta.get();
            cuponRepository.delete(cupon);
        }
    }
    
    @Transactional
    public List<Cupon>findAllAndEstado(){
        List<Cupon> cuponList = (List<Cupon>) cuponRepository.findByIdAndEstadoTrue();
        return cuponList;
        
    }

        */
}
