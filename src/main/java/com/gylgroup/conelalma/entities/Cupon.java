package com.gylgroup.conelalma.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.*;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Getter
@Setter
public class Cupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 64)
    @NotEmpty(message = "El codigo es obligatorio")
    @Size(min = 2, max = 64, message = "Debe tener min 2 caracteres y menos de 64")
    @Pattern(regexp = "[a-zA-Z0-9 ]{2,64}", message = "Debe contener letras y numeros solamente.")
    private String codigo;

    private Boolean estado;

    @Min(0)
    @Max(100)
    @NotNull(message = "tiene que asignarse un descuento")
    private Integer descuento;

    @NotNull(message = "Tiene que asignarse la fecha de creación")
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @NotNull(message = "Tiene que asignarse la fecha de modificación")
    @LastModifiedDate
    private LocalDateTime fechaModificacion;

    @NotNull(message = "Debe asignarse un usuario")
    @OneToOne
    private Usuario usuario;
}
