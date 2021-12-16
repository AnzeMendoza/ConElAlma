package com.gylgroup.conelalma.entities;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.gylgroup.conelalma.enums.TipoEvento;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private TipoEvento tipoEvento;

    @NotNull(message = "Lista de combos debe ser asignado")
    @OneToMany
    private List<Combos> listaCombos;

    @NotNull(message = "Debe asignarse cantidad de comensales.")
    @Min(value = 0, message = "tiene que ser mayor a 0")
    private Integer cantidadBaseComensales;

    @NotNull(message = "Debe asignarse el precio base.")
    @Min(value = 0, message = "tiene que ser mayor a 0")
    private Integer precioBase;

    private String foto;

    private Boolean estado;
}
