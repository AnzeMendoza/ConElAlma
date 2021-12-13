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

    @Autowired
    private CuponRepository cuponRepository;

    @Transactional
    public void save(String codigo, Integer descuento) {
        Cupon cupon = new Cupon();
        LocalDateTime date = LocalDateTime.now();
        cupon.setCodigo(codigo);
        cupon.setDescuento(descuento);
        cupon.setEstado(Boolean.TRUE);
        cupon.setFechaCreacion(date);
        cupon.setFechaModificacion(date);

        cuponRepository.save(cupon);
    }

    @Transactional
    public void update(Integer id, String codigo, Integer descuento, Integer idUsuario) throws Exception {
        Optional<Cupon> respuesta = cuponRepository.findById(id);
        if (respuesta.isPresent()) {
            Cupon cupon = respuesta.get();
            cupon.setCodigo(codigo);
            cupon.setDescuento(descuento);
            cupon.setFechaModificacion(LocalDateTime.now());
            cuponRepository.save(cupon);
        } else {
            throw new Exception("No se pudo encontrar el cupon para modificar");
        }
    }

    @Transactional
    public List<Cupon> findAll() throws Exception {
        List<Cupon> cuponList = cuponRepository.findAll();
        if (cuponList.size() > 0) {
            return cuponList;
        } else {
            throw new Exception("No hay cupones cargados");

        }
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


/*    @Transactional(readOnly = true)
    public Cupon findByNombre(String nombre) throws Exception {
        Cupon cupon = cuponRepository.findByNombre(nombre);
        if (cupon != null) {
            return cupon;
        } else {
            throw new Exception("No se pudo encontrar el cupón por Nombre");
        }
    }*/

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
            Cupon cupon = respuesta.get();
            cupon.setEstado(Boolean.TRUE);
            cupon.setFechaModificacion(LocalDateTime.now());
            cuponRepository.save(cupon);
        } else {
            throw new Exception("No se pudo habilitar el cupón porque no se encontró el ID");
        }
    }

    @Transactional
    public void disable(Integer id) throws Exception {
        Optional<Cupon> respuesta = cuponRepository.findById(id);
        if (respuesta.isPresent()) {
            Cupon cupon = respuesta.get();
            cupon.setEstado(Boolean.FALSE);
            cupon.setFechaModificacion(LocalDateTime.now());
            cuponRepository.save(cupon);
        } else {
            throw new Exception("No se pudo hablitar el cupón porque no se encontró el ID");
        }
    }

    @Transactional
    public List<Cupon> findAllAndEstado() throws Exception {
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
}
