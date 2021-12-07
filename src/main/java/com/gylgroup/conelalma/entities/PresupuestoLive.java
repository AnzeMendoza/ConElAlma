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
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.gylgroup.conelalma.enums.TipoEvento;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

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

    @NotNull(message = "Menu debe ser asignado")
    @OneToOne
    private Menu menu;

    @NotNull(message = "Menu debe ser asignado")
    @OneToOne
    private Bebida bebida;

    @NotNull(message = "La cantidad de comensales debe ser mayor a 0")
    @Min(value = 0, message = "La cantidad de comensales debe ser mayor a 0")
    private Integer cantidadComensales;

    @NotNull(message = "Local debe ser asignado")
    @OneToOne
    private Local local;

    @NotNull(message = "Cupon debe ser asignado")
    @OneToOne
    private Cupon cupon;

    @NotNull(message = "Debe asignarse el precio final.")
    @Min(value = 0, message = "tiene que ser mayor a 0")
    private Double precioFinal;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime fechaPresupuesto;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime fechaEventoSolicitada;

    @NotNull(message = "Usuario debe ser asignado")
    @ManyToOne
    private Usuario usuario;

    private Boolean estado;

    @NotNull(message = "Reserva debe ser asignado")
    @OneToMany(mappedBy = "presupuestoLive")
    private List<Reserva> reservas;
}
