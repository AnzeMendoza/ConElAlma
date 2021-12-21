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
        Reserva reserva = new Reserva();
        reserva.setTipoDePago(TipoDePago.TARJETA);
        presupuestoLive.setReservas(Collections.singletonList(reserva));
        return presupuestoLiveRepository.save(presupuestoLive);
    }

    @Transactional
    public PresupuestoLive update(PresupuestoLive presupuestoLiveActualizado, Integer id) throws Exception {
        existsById(id);
        return save(presupuestoLiveActualizado);
    }

    @Transactional
    public void delete(Integer id) throws Exception {
        existsById(id);
        presupuestoLiveRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public PresupuestoLive findById(Integer id) throws Exception {
        existsById(id);
        return presupuestoLiveRepository.getById(id);
    }

    @Transactional(readOnly = true)
    public List<PresupuestoLive> findAll() {
        return presupuestoLiveRepository.findAll();
    }

    @Transactional
    public void enable(Integer id) throws Exception {
        existsById(id);
        changeStatus(id, true);
    }

    @Transactional
    public void disable(Integer id) throws Exception {
        existsById(id);
        changeStatus(id, false);
    }

    @Transactional
    public List<PresupuestoLive> findByEstadoTrue(){
        return presupuestoLiveRepository.findByEstadoTrue();
    }

    private void existsById(Integer id) throws Exception {
        if (!presupuestoLiveRepository.existsById(id)) {
            throw new Exception();
        }
    }

    private void changeStatus(Integer id, boolean status) {
        PresupuestoLive presupuestoLive = presupuestoLiveRepository.getById(id);
        presupuestoLive.setEstado(status);
        presupuestoLiveRepository.save(presupuestoLive);
    }
}
