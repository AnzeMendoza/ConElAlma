
package com.gylgroup.conelalma.repositories;

import com.gylgroup.conelalma.entities.Cupon;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



@Repository
public interface CuponRepository extends JpaRepository<Cupon, Integer>{ 
    Cupon findByCodigo(@Param("codigo") String codigo);

    Cupon findByDescuento(@Param("id") Integer descuento);

    List<Cupon>findAllByEstado(@Param("estado") Boolean estado);
}
