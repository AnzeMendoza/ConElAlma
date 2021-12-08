
package com.gylgroup.conelalma.repositories;

import com.gylgroup.conelalma.entities.Cupon;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



@Repository
public interface CuponRepository extends JpaRepository<Cupon, Integer>{

    //Vamos a usar querys personalizadas
    //@Query(value = "select * from cupon where nombre=:nombre", nativeQuery = true)
    //Cupon findByNombre(@Param("nombre") String nombre);

    @Query(value = "select * from cupon where codigo=:codigo", nativeQuery = true)
    Cupon findByCodigo(@Param("codigo") String codigo);

    @Query(value = "select * from cupon where id=:id", nativeQuery = true)
    Cupon findByDescuento(@Param("id") Integer descuento);

    @Query(value = "select * from cupon where estado=:estado", nativeQuery = true)
    List<Cupon>findAllAndEstado(@Param("estado") Boolean estado);
}
