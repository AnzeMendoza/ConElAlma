package com.gylgroup.conelalma.entities;

import javax.persistence.*;

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
@SQLDelete(sql = "UPDATE menu SET estado = false WHERE id = ?")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private TipoEvento tipoEvento;

    @OneToMany
    private List<Combos> listaCombos;

    private Integer cantidadBaseComensales;

    private Double precioBase;

    private String foto;

    private Boolean estado;
}
