package com.gylgroup.conelalma.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@SQLDelete(sql = "UPDATE combos SET estado = false WHERE id = ?")
public class Combos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 64)
    @NotEmpty(message = "El nombre es obligatorio")
    @Size(min = 2, max = 64, message = "Debe tener min 2 caracteres y menos de 64")
    @Pattern(regexp = "[a-zA-Z ]{2,64}", message = "Debe contener letras.")
    private String nombre;

    @NotNull
    @OneToMany
    private List<Bebida> listaBebidas;

    @NotNull
    @OneToMany
    private List<Comida> listaComida;

    @NotNull(message = "El precio combo es obligatorio.")
    @Min(value = 0, message = "tiene que ser mayor a 0")
    private Double precioCombo;

    private Boolean estado;

    //@NotNull(message = "Tiene que estar asignado")
    //@NotEmpty(message = "Debe incluir el path de foto")
    private String foto;
}
