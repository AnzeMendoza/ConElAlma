
package com.gylgroup.conelalma.repositories;

import com.gylgroup.conelalma.entities.Cupon;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface CuponRepository extends JpaRepository<Cupon, Integer>{
    
    Cupon findByNombre(String nombre);
    
    Cupon findByCodigo(String codigo);
    
    Cupon findByDescuento(String descuento);
    
    List<Cupon>findAllAndEstado(Boolean estado);
}
