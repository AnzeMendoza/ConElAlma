package com.gylgroup.conelalma.services;

import com.gylgroup.conelalma.entities.Reserva;
import com.gylgroup.conelalma.repositories.ReservaRepository;
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
    public void save(Reserva dto){
        Reserva reserva = new Reserva();

        reserva.setPresupuestoLive(dto.getPresupuestoLive());
        reserva.setFechaReserva(dto.getFechaReserva());
        reserva.setEstado(true);
        reserva.setTipoDePago(dto.getTipoDePago());
        reserva.setPagoEfectuado(dto.getPagoEfectuado());

        reservaRepository.save(reserva);
    }

    @Transactional
    public void update(Reserva dto) throws Exception{
        Reserva reserva = reservaRepository.findById(dto.getId()).orElseThrow(()-> new Exception("No existe la reserva asociada al ID: "+dto.getId()));

        reserva.setPresupuestoLive(dto.getPresupuestoLive());
        reserva.setFechaReserva(dto.getFechaReserva());
        reserva.setEstado(dto.getEstado());
        reserva.setTipoDePago(dto.getTipoDePago());
        reserva.setPagoEfectuado(dto.getPagoEfectuado());

        reservaRepository.save(reserva);
    }

    @Transactional
    public Reserva findById(Integer id){
        Optional<Reserva> optional = reservaRepository.findById(id);
        return optional.orElse(null);
    }

    @Transactional
    public List<Reserva> findAll(){
        return reservaRepository.findAll();
    }

    @Transactional
    public List<Reserva> findAllEnable(){
        return reservaRepository.findAllEnable();
    }

    @Transactional
    public List<Reserva> findByFecha(Date fechaReserva){
        return reservaRepository.findByFecha(fechaReserva);
    }

    @Transactional
    public void disable(Integer id){
        if(reservaRepository.existsReservaById(id)){
            reservaRepository.disable(id);
        }
    }

    @Transactional
    public void enable(Integer id){
        if(reservaRepository.existsReservaById(id)){
            reservaRepository.enable(id);
        }
    }

}
