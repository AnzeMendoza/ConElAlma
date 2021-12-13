package com.gylgroup.conelalma.entities;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.gylgroup.conelalma.enums.TipoEvento;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Getter
@Setter
public class PresupuestoLive {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private TipoEvento tipoEvento;

/*
    @NotNull(message = "Menu debe ser asignado")
    @OneToOne
    private Menu menu;

    @NotNull(message = "Menu debe ser asignado")
    @OneToOne
    private Bebida bebida;

    @NotNull(message = "Local debe ser asignado")
    @OneToOne
    private Local local;

    @NotNull(message = "Cupon debe ser asignado")
    @OneToOne
    private Cupon cupon;

    @NotNull(message = "Usuario debe ser asignado")
    @ManyToOne
    private Usuario usuario;

    @NotNull(message = "Reserva debe ser asignado")
    @OneToMany(mappedBy = "presupuestoLive")
    private List<Reserva> reservas;

*/
    @NotNull(message = "La cantidad de comensales debe ser mayor a 0")
    @Min(value = 0, message = "La cantidad de comensales debe ser mayor a 0")
    private Integer cantidadComensales;

    @NotNull(message = "Debe asignarse el precio final.")
    @Min(value = 0, message = "tiene que ser mayor a 0")
    private Double precioFinal;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "La fecha de evento debe ser asignada")
    private Date fechaPresupuesto;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "La fecha de evento debe ser asignada")
    private Date fechaEventoSolicitada;

    private Boolean estado;

    public PresupuestoLive() {
        fechaPresupuesto = new Date();
        estado = true;
    }
}
