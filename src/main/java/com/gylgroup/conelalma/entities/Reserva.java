package com.gylgroup.conelalma.entities;

import com.gylgroup.conelalma.enums.TipoDePago;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Getter
@Setter
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private PresupuestoLive presupuestoLive;

    @ManyToOne
    private Usuario usuario;

    @Temporal(TemporalType.DATE)
    @NotNull(message = "la fecha de reserva tiene que ser asignada")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaReserva;

    private Boolean estado;

    @Enumerated(EnumType.STRING)
    private TipoDePago tipoDePago;

    private Boolean pagoEfectuado;

    public Reserva() {
        estado = true;
        pagoEfectuado = false;
    }
}
