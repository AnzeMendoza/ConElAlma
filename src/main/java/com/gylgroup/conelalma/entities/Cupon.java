package com.gylgroup.conelalma.entities;

import lombok.Getter;
import lombok.Setter;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

@Entity
// @EntityListeners(AuditingEntityListener.class)
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

    @Min(value = 0, message = "Descuento tiene que ser mayor a 0 y menos a 100")
    @Max(value = 100, message = "Descuento tiene que ser mayor a 0 y menos a 100")
    @NotNull(message = "tiene que asignarse un descuento")
    private Integer descuento;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyy-MM-dd")
    @NotNull(message = "Tiene que asignarse la fecha de creación")
    // @CreatedDate
    @Column(nullable = false, updatable = false)
    private Date fechaCreacion;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyy-MM-dd")
    @NotNull(message = "Tiene que asignarse la fecha de modificación")
    // @CreatedDate
    @Column(nullable = false, updatable = false)
    private Date fechaModificacion;

    /* Todo ver porque tiene que estar asociado a un Usuario, no tiene logica
     * @NotNull(message = "Debe asignarse un usuario")
     * 
     * @OneToOne
     * private Usuario usuario;
     */

    public Cupon() {
        setEstado(Boolean.TRUE);
        setFechaCreacion(new Date());
        setFechaModificacion(new Date());
    }
}
