package com.gylgroup.conelalma.services;

import com.gylgroup.conelalma.entities.PresupuestoLive;
import com.gylgroup.conelalma.enums.TipoEvento;
import com.gylgroup.conelalma.repositories.PresupuestoLiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class PresupuestoLiveService {

    @Autowired
    private PresupuestoLiveRepository presupuestoLiveRepository;

/*    @Autowired
    private MenuService menuService;

    @Autowired
    private BebidaService bebidaService;

    // ok
    @Autowired
    private LocalService localService;

    // ok
    @Autowired
    private CuponService cuponService;*/

    @Autowired
    private UsuarioService usuarioService;

/*    public PresupuestoLive crearPresupuestoLive(
            TipoEvento tipoEvento,
            Integer menuId,
            Integer bebidaId,
            Integer cantidadComensales,
            Integer locadId,
            Integer cuponId,
            Double precioFinal,
            LocalDateTime fechaPresupuesto,
            LocalDateTime fechaEventoSolicitada,
            Integer usuarioId,
            Boolean estado
    ) {
        PresupuestoLive presupuestoLive = new PresupuestoLive();

        presupuestoLive.setTipoEvento(tipoEvento);
        presupuestoLive.setMenu(menuService.getById(menuId));
        presupuestoLive.setBebida(bebidaService.getById(bebidaId));
        presupuestoLive.setCandidadComensales(cantidadComensales);
        presupuestoLive.setLocal(localService.getById(locadId));
        presupuestoLive.setCupon(cuponService.getById(cuponId));
        presupuestoLive.setPrecioFinal(precioFinal);
        presupuestoLive.setFechaPresupuesto(fechaPresupuesto);
        presupuestoLive.setFechaEventoSolicitada(fechaEventoSolicitada);
        presupuestoLive.setUsuario(usuarioService.getById(usuarioId));
        presupuestoLive.setEstado(estado);

        return save(presupuestoLive);
    }*/

    public PresupuestoLive crearPresupuestoLive(
            TipoEvento tipoEvento,
            Integer cantidadComensales,
            Double precioFinal,
            Date fechaPresupuesto,
            Date fechaEventoSolicitada,
            Boolean estado
    ) {
        PresupuestoLive presupuestoLive = new PresupuestoLive();

        presupuestoLive.setTipoEvento(tipoEvento);
        presupuestoLive.setCantidadComensales(cantidadComensales);
        presupuestoLive.setPrecioFinal(precioFinal);
        presupuestoLive.setFechaPresupuesto(fechaPresupuesto);
        presupuestoLive.setFechaEventoSolicitada(fechaEventoSolicitada);
        presupuestoLive.setEstado(estado);

        return save(presupuestoLive);
    }

    @Transactional
    public PresupuestoLive save(PresupuestoLive presupuestoLive) {
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
