
package com.gylgroup.conelalma.repositories;

import com.gylgroup.conelalma.entities.Cupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CuponRepository extends JpaRepository<Cupon, Integer> {
    Cupon findByCodigo(@Param("codigo") String codigo);

    Cupon findByDescuento(@Param("id") Integer descuento);

    List<Cupon> findAllByEstado(@Param("estado") Boolean estado);

    boolean existsById(int id);
}
