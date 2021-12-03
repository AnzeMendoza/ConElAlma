package com.gylgroup.conelalma.entities;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

@Entity
@NoArgsConstructor
@Getter
@Setter
@SQLDelete(sql = "UPDATE comida SET estado = false WHERE id = ?")
public class Comida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;
    private Double precioUnitario;
    private Boolean estado;


}