
package com.gylgroup.conelalma.repositories;

import com.gylgroup.conelalma.entities.Cupon;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface CuponRepository extends JpaRepository<Cupon, Integer>{
    
/*    Optional<Cupon> findByNombre(String nombre);

    Optional<Cupon> findByCodigo(String codigo);

    Optional<Cupon> findByDescuento(String descuento);
    
    List<Cupon> findByIdAndEstadoTrue();*/
}
