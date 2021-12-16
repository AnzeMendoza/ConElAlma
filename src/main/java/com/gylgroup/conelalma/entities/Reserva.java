package com.gylgroup.conelalma.entities;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.gylgroup.conelalma.enums.TipoDePago;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private PresupuestoLive presupuestoLive;

    private LocalDateTime fechaReserva;
    private Boolean estado;

    @Enumerated(EnumType.STRING)
    private TipoDePago tipoDePago;

    private Boolean pagoEfectuado;
}
