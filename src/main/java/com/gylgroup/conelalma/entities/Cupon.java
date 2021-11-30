package com.gylgroup.conelalma.entities;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import static javax.swing.text.StyleConstants.Size;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

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
    @NotEmpty(message = "El nombre es obligatorio")
    @Size(min = 2, max = 64, message = "Debe tener min 2 caracteres y menos de 64")
    @Pattern(regexp = "[a-zA-Z ]{2,64}", message = "Debe contener solo letras.")
    private String nombre;
    
    @Column(length = 64)
    @NotEmpty(message = "El nombre es obligatorio")
    @Size(min = 2, max = 64, message = "Debe tener min 2 caracteres y menos de 64")
    @Pattern(regexp = "[a-zA-Z ]{2,64}", message = "Debe contener solo letras.")
    private String codigo;
    private Boolean estado;
    private Integer descuento;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyy-MM-dd")
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyy-MM-dd")
    @PastOrPresent(message = "La fecha debe ser actual o anterior a la de hoy")
    @LastModifiedDate
    private LocalDateTime fechaModificacion;

    @OneToOne
    private Usuario usuario;
}
