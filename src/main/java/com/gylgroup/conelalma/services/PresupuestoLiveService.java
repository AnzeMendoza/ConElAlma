package com.gylgroup.conelalma.services;

import com.gylgroup.conelalma.entities.PresupuestoLive;
import com.gylgroup.conelalma.entities.Reserva;
import com.gylgroup.conelalma.enums.TipoDePago;
import com.gylgroup.conelalma.repositories.PresupuestoLiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class PresupuestoLiveService {

    @Autowired
    private PresupuestoLiveRepository presupuestoLiveRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private CuponService cuponService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private ReservaService reservaService;

    @Transactional
    public PresupuestoLive save(PresupuestoLive presupuestoLive) {
        presupuestoLive.setCupon(cuponService.findByCodigo(presupuestoLive.getCodCupon()));
        presupuestoLive.setMenu(menuService.findById(presupuestoLive.getMenu().getId()));
        presupuestoLive.getCantidadComensales();

        presupuestoLive.setReservas(Collections.emptyList());
        return presupuestoLiveRepository.save(presupuestoLive);
    }

    @Transactional
    public PresupuestoLive update(Integer id, PresupuestoLive presupuestoLiveActualizado) throws Exception {
        if (!presupuestoLiveRepository.existsById(id)) {
            throw new Exception("no se encontro el id");
        }
        return save(presupuestoLiveActualizado);
    }

    @Transactional
    public void delete(Integer id) throws Exception {
        if (!presupuestoLiveRepository.existsById(id)) {
            throw new Exception("no se encontro el id");
        }
        presupuestoLiveRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public PresupuestoLive findById(Integer id) throws Exception {
        if (!presupuestoLiveRepository.existsById(id)) {
            throw new Exception("no se encontro el id");
        }
        return presupuestoLiveRepository.getById(id);
    }

    @Transactional(readOnly = true)
    public List<PresupuestoLive> findAll() {
        return presupuestoLiveRepository.findAll();
    }

    @Transactional
    public void enable(Integer id) throws Exception {
        if (!presupuestoLiveRepository.existsById(id)) {
            throw new Exception("no se encontro el id");
        }
        changeStatus(id, true);
    }

    @Transactional
    public void disable(Integer id) throws Exception {
        if (!presupuestoLiveRepository.existsById(id)) {
            throw new Exception("no se encontro el id");
        }
        changeStatus(id, false);
    }

    @Transactional
    public List<PresupuestoLive> findByEstadoTrue() {
        return presupuestoLiveRepository.findByEstadoTrue();
    }

    @Transactional
    public boolean existsById(int id) {
        return presupuestoLiveRepository.existsById(id);
    }

    @Transactional
    public PresupuestoLive findByIdUsuario(Integer idUsuario){
        return presupuestoLiveRepository.findByIdUsuario(idUsuario);
    }

    private void changeStatus(Integer id, boolean status) {
        PresupuestoLive presupuestoLive = presupuestoLiveRepository.getById(id);
        presupuestoLive.setEstado(status);
        presupuestoLiveRepository.save(presupuestoLive);
    }
}
