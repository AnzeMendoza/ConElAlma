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
    public void save(Reserva reserva) {
        reservaRepository.save(reserva);
    }

    @Transactional
    public void update(int id, Reserva reserva) throws Exception {
        if (!reservaRepository.existsById(id)) {
            throw new Exception("No se pudo actualizar el registro");
        }
        reservaRepository.save(reserva);
    }

    @Transactional
    public Reserva findById(Integer id) {
        Optional<Reserva> optional = reservaRepository.findById(id);
        return optional.get();
    }

    @Transactional
    public List<Reserva> findAll() {
        return reservaRepository.findAll();
    }

    @Transactional
    public List<Reserva> findByFecha(Date fechaReserva) {
        return reservaRepository.findByFecha(fechaReserva);
    }

    @Transactional
    public void disable(Integer id) throws Exception {
        if (!reservaRepository.existsById(id)) {
            throw new Exception("No se pudo actualizar el registro");
        }
        changeStatus(id, false);
    }

    @Transactional
    public void enable(Integer id) throws Exception {
        if (!reservaRepository.existsById(id)) {
            throw new Exception("No se pudo actualizar el registro");
        }
        changeStatus(id, true);
    }

    private void changeStatus(Integer id, boolean status) {
        Reserva reserva = reservaRepository.getById(id);
        reserva.setEstado(status);
        reservaRepository.save(reserva);
    }

    @Transactional
    public boolean existsById(int id){
        return reservaRepository.existsById(id);
    }

}