package com.gylgroup.conelalma.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
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

    private String nombre;

    @OneToMany
    private List<Bebida> listaBebidas;
    @OneToMany
    private List<Comida> listaComida;

    private Double precioCombo;
    private Boolean estado;
    private String foto;

}
