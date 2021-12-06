package com.gylgroup.conelalma.services;

import com.gylgroup.conelalma.entities.Reserva;
import com.gylgroup.conelalma.repositorys.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Transactional
    public void crearReserva(Reserva dto){
        Reserva reserva = new Reserva();

        reserva.setPresupuestoLive(dto.getPresupuestoLive());
        reserva.setFechaReserva(dto.getFechaReserva());
        reserva.setEstado(dto.getEstado());
        reserva.setTipoDePago(dto.getTipoDePago());
        reserva.setPagoEfectuado(dto.getPagoEfectuado());

        reservaRepository.save(reserva);
    }

    @Transactional
    public void modificarReserva(Reserva dto) throws Exception{
        Reserva reserva = reservaRepository.findById(dto.getId()).orElseThrow(()-> new Exception("No existe la reserva asociada al ID: "+dto.getId()));

        reserva.setPresupuestoLive(dto.getPresupuestoLive());
        reserva.setFechaReserva(dto.getFechaReserva());
        reserva.setEstado(dto.getEstado());
        reserva.setTipoDePago(dto.getTipoDePago());
        reserva.setPagoEfectuado(dto.getPagoEfectuado());

        reservaRepository.save(reserva);
    }

    @Transactional
    public Reserva buscarPorId(Integer id){
        Optional<Reserva> optional = reservaRepository.findById(id);
        return optional.orElse(null);
    }

    @Transactional
    public List<Reserva> traerTodas(){
        return reservaRepository.findAll();
    }

    @Transactional
    public List<Reserva> traerAltas(){
        return reservaRepository.traerAltas();
    }

    @Transactional
    public List<Reserva> traerReservasFecha(Date fechaReserva){
        return reservaRepository.traerPorFecha(fechaReserva);
    }

    @Transactional
    public void bajaReserva(Integer id){
        reservaRepository.deleteById(id);
    }

    @Transactional
    public void altaReserva(Integer id){
        reservaRepository.habilitar(id);
    }

}
