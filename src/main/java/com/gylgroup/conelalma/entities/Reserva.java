package com.gylgroup.conelalma.entities;


import java.util.Date;

import javax.persistence.*;

import com.gylgroup.conelalma.enums.TipoDePago;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.springframework.format.annotation.DateTimeFormat;



@Entity
@NoArgsConstructor
@Getter
@Setter
@SQLDelete(sql = "UPDATE reserva SET estado = false WHERE id = ?")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @ManyToOne
    private PresupuestoLive presupuestoLive;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaReserva;

    private Boolean estado;

    @Enumerated(EnumType.STRING)
    private TipoDePago tipoDePago;

    private Boolean pagoEfectuado;
}
