package com.gylgroup.conelalma.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Comida {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(length = 64)
    @Size(min = 2, max = 64, message = "Debe tener min 2 caracteres y menos de 64")
    @Pattern(regexp = "[a-zA-Z0-9 ]{2,64}", message = "Debe contener letras y numeros solamente.")
    @NotNull(message = "Nombre tiene que estar asignado")
    @NotEmpty(message="El nombre no puede ser nulo")
    private String nombre;

    @NotNull(message="Debe de asignarle un precio")
    @Min(value = 0, message = "tiene que ser mayor a 0")
    private Double precioUnitario;

    private boolean estado;
}



