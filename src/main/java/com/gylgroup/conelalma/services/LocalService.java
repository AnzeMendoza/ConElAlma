package com.gylgroup.conelalma.services;

import com.gylgroup.conelalma.entities.Local;
import com.gylgroup.conelalma.exception.ExceptionService;
import com.gylgroup.conelalma.repositories.LocalRepository;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class LocalService {

    @Autowired
    private LocalRepository localRepository;

    @Autowired
    private FotoService fotoService;

    @Transactional
    public void save(Local miLocal, MultipartFile foto) throws ExceptionService {
        Local local = new Local();
        local.setNombre(miLocal.getNombre());
        local.setCantidadMaximaPersonas(miLocal.getCantidadMaximaPersonas());
        local.setDireccion(miLocal.getDireccion());
        local.setFoto(miLocal.getFoto());
        local.setPrecio(miLocal.getPrecio());
        local.setEstado(true);
        if (!foto.isEmpty()) {
            local.setFoto(fotoService.saveFile(foto));
        } else {
            local.setFoto("");
        }
        localRepository.save(local);
    }

    @Transactional
    public void update(Local miLocal, MultipartFile foto) throws ExceptionService {
        if (localRepository.existsById(miLocal.getId())) {
            Local local = localRepository.BuscarLocalPorId(miLocal.getId());
            local.setNombre(miLocal.getNombre());
            local.setCantidadMaximaPersonas(miLocal.getCantidadMaximaPersonas());
            local.setDireccion(miLocal.getDireccion());
            local.setFoto(miLocal.getFoto());
            local.setPrecio(miLocal.getPrecio());
            local.setEstado(true);
            if (!foto.isEmpty()) {
                local.setFoto(fotoService.saveFile(foto));
            } else {
                local.setFoto("");
            }
            localRepository.save(local);
        }
    }

    @Transactional
    public List<Local> findAll() {
        return localRepository.findAll();
    }

    @Transactional
    public List<Local> findAllAndEstado() {
        return localRepository.findAllByActivo();
    }

    @Transactional
    public Local findById(Integer id) {
        return localRepository.BuscarLocalPorId(id);
    }

    @Transactional
    public List<Local> filtrarPorPrecioMax(Double precio) {
        return localRepository.BuscarPorPrecioMaximo(precio);
    }

    @Transactional
    public List<Local> filtrarPorCapacidad(Integer capacidad) {
        return localRepository.BuscarPorCantidadPersonas(capacidad);
    }

    @Transactional
    public List<Local> filtrarPorUbicacion(String ubicacion) {
        return localRepository.BuscarPorDireccion(ubicacion);
    }

    @Transactional
    public boolean existsLocal(Integer id) {
        return localRepository.existsById(id);
    }

    @Transactional
    public void disable(Integer id) {
        changeStatus(id, false);
    }

    @Transactional
    public void enable(Integer id) {
        changeStatus(id, true);
    }

    private void changeStatus(Integer id, boolean status) {
        Local local = localRepository.getById(id);
        local.setEstado(status);
        localRepository.save(local);
    }
}
