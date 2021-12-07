package com.gylgroup.conelalma.entities;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.gylgroup.conelalma.enums.TipoEvento;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class PresupuestoLive {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private TipoEvento tipoEvento;

    @OneToOne
    private Menu menu;


    @OneToOne
    private Bebida bebida;

    private Integer candidadComensales;

    @OneToOne
    private Local local;

    @OneToOne
    private Cupon cupon;

    private Double precioFinal;

    private LocalDateTime fechaPresupuesto;
    private LocalDateTime fechaEventoSolicitada;

    @ManyToOne
    private Usuario usuario;

    private Boolean estado;

    @OneToMany(mappedBy = "presupuestoLive")
    private List<Reserva> reservas;
}
