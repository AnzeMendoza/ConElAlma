package com.gylgroup.conelalma.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Bebida {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 64)
    @NotNull(message = "nombre no debe ser null")
    @NotEmpty(message = "El nombre es obligatorio")
    @Size(min = 2, max = 64, message = "Debe tener min 2 caracteres y menos de 64")
    @Pattern(regexp = "[a-zA-Z0-9 ]{2,64}", message = "Debe contener letras y numeros solamente.")
    private String nombre;

    @NotNull(message = "El precio unitario tiene que estar.")
    @Min(value = 0, message = "tiene que ser mayor a 0")
    private Double precioUnitario;

    private Boolean estado;
}

