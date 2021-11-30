package com.gylgroup.conelalma.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Local {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @NotNull(message ="La direccion es obligatoria")
    @NotEmpty(message ="La direccion es obligatoria")
    private String direccion;
    @NotNull(message ="La cantidad de Personas no puede ser nulla")
    private Integer cantidadMaximaPersonas;
    @NotNull(message = "El precio no puede ser nullo")
    private Double precio;
    private String foto;
}
