package com.gylgroup.conelalma.services;

import com.gylgroup.conelalma.entities.Cupon;
import com.gylgroup.conelalma.repositories.CuponRepository;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        Optional<Cupon> respuesta = cuponRepository.findById(id);
        if (respuesta.isPresent()) {
            Date date = new Date();
            Cupon opcupon = respuesta.get();
            opcupon.setCodigo(cupon.getCodigo());
            opcupon.setDescuento(cupon.getDescuento());
            opcupon.setFechaModificacion(date);
            cuponRepository.save(opcupon);
        } else {
            throw new Exception("No se pudo encontrar el cupon para modificar");
        }
    }

    @Transactional
    public List<Cupon> findAll() {
            return  cuponRepository.findAll();
    }

    @Transactional
    public Cupon findById(Integer id) throws Exception {
        Optional<Cupon> respuesta = cuponRepository.findById(id);
        if (respuesta.isPresent()) {
            Cupon cupon = respuesta.get();
            return cupon;
        } else {
            throw new Exception("No se pudo encontrar el cupon por ID");

        }
    }


    @Transactional(readOnly = true)
    public Cupon findByCodigo(String codigo) throws Exception {
        Cupon cupon = cuponRepository.findByCodigo(codigo);
        if (cupon != null) {
            return cupon;
        } else {
            throw new Exception("No se pudo encontrar el cupón por Código");
        }
    }

    @Transactional(readOnly = true)
    public Cupon findByDescuento(Integer descuento) throws Exception {
        Cupon cupon = cuponRepository.findByDescuento(descuento);
        if (cupon != null) {
            return cupon;
        } else {
            throw new Exception("No se pudo encontrar el cupón por Descuento");
        }
    }

    @Transactional
    public void enable(Integer id) throws Exception {
        Optional<Cupon> respuesta = cuponRepository.findById(id);
        if (respuesta.isPresent()) {
            Date date = new Date();
            Cupon cupon = respuesta.get();
            cupon.setEstado(Boolean.TRUE);
            cupon.setFechaModificacion(date);
            cuponRepository.save(cupon);
        } else {
            throw new Exception("No se pudo habilitar el cupón porque no se encontró el ID");
        }
    }

    @Transactional
    public void disable(Integer id) throws Exception {
        Optional<Cupon> respuesta = cuponRepository.findById(id);
        if (respuesta.isPresent()) {
            Date date = new Date();
            Cupon cupon = respuesta.get();
            cupon.setEstado(Boolean.FALSE);
            cupon.setFechaModificacion(date);
            cuponRepository.save(cupon);
        } else {
            throw new Exception("No se pudo hablitar el cupón porque no se encontró el ID");
        }
    }

    @Transactional
    public List<Cupon> findAllByEstado() throws Exception {
        List<Cupon> cuponList = (List<Cupon>) cuponRepository.findAllByEstado(Boolean.TRUE);
        if (cuponList.size() > 0) {
            return cuponList;
        } else {
            throw new Exception("No se encontraron cupones habilitados");
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


/*    @Transactional
    public List<Cupon>findAllAndEstado(){
        List<Cupon> cuponList = (List<Cupon>) cuponRepository.findBy();
        return cuponList;
        
    }*/

}
