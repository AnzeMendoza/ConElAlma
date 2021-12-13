package com.gylgroup.conelalma.entities;

import javax.persistence.*;
import javax.validation.constraints.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Local {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 64)
    @NotEmpty(message ="El nombre es obligatorio")
    @NotNull(message = "Se debe asignar un nombre")
    @Size(min = 2, max = 64, message = "Debe tener min 2 caracteres y menos de 64")
    @Pattern(regexp = "[a-zA-Z0-9 ]{2,64}", message = "Debe contener letras y numeros solamente.")
    private String nombre;

    @Column(length = 64)
    @NotEmpty(message ="El dirección es obligatorio")
    @NotNull(message = "Se debe asignar una dirección")
    @Size(min = 2, max = 64, message = "Debe tener min 2 caracteres y menos de 64")
    @Pattern(regexp = "[a-zA-Z0-9 ]{2,64}", message = "Debe contener letras y numeros solamente.")
    private String direccion;

    @NotNull(message ="La cantidad de Personas no puede ser nula")
    @Min(value = 0, message = "Debe ser mayor a 0")
    private Integer cantidadMaximaPersonas;

    @NotNull(message = "El precio no puede ser nulo")
    @Min(value = 0, message = "Debe ser mayor a 0")
    private Double precio;

    //@NotNull(message = "Tiene que estar asignado")
    //@NotEmpty(message = "Debe incluir el path de foto")
    private String foto;

    private boolean estado;
}
