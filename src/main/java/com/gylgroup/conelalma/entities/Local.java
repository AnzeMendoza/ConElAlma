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
import org.hibernate.annotations.SQLDelete;

@Entity
@NoArgsConstructor
@Getter
@Setter
@SQLDelete(sql = "UPDATE Local SET estado = false WHERE id = ?")
public class Local {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @NotNull
    @NotEmpty(message ="La direccion es obligatoria")
    private String direccion;
    @NotNull(message ="La cantidad de Personas no puede ser nulla")
    private Integer cantidadMaximaPersonas;
    @NotNull(message = "El precio no puede ser nullo")
    private Double precio;
    private String foto;
    private boolean estado;
}
