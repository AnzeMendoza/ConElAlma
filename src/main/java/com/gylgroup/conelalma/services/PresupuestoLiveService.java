package com.gylgroup.conelalma.services;

import com.gylgroup.conelalma.entities.PresupuestoLive;
import com.gylgroup.conelalma.enums.TipoEvento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.TransactionScoped;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
public class PresupuestoLiveService {

    @Autowired
    private PresupuestoLiveService presupuestoLiveService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private BebidaService bebidaService;

    @Autowired
    private LocalService localService;

    @Autowired
    private CuponService cuponService;

    @Autowired
    private UsuarioService usuarioService;

    public PresupuestoLive crearPresupuestoLive(
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

        return presupuestoLive;
    }

    @Transactional
    public PresupuestoLive save(PresupuestoLive presupuestoLive){
        
    }

}
