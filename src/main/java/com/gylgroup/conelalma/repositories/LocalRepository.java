package com.gylgroup.conelalma.repositories;

import com.gylgroup.conelalma.entities.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocalRepository extends JpaRepository<Local,Integer> {
    @Query(value = "SELECT * FROM local WHERE id =:id", nativeQuery = true)
    Local BuscarLocalPorId(@Param("id")Integer id);  
    
    @Query(value = "SELECT * FROM local WHERE precio <=: maximo", nativeQuery = true)
    List<Local> BuscarPorPrecioMaximo(@Param("maximo") Double maximo);

    @Query(value = "SELECT * FROM local WHERE cantidadMaximaPersonas >=: maxima", nativeQuery = true)
    List<Local> BuscarPorCantidadPersonas(@Param("maxima") Integer maxima);
    
    @Query(value = "SELECT * FROM local WHERE direccion =: direccion", nativeQuery = true)
    List<Local> BuscarPorDireccion(@Param("direccion") String direccion);
    
    @Query(value = "SELECT * FROM local WHERE estado = 1", nativeQuery = true)
    List<Local> findAllByActivo();
}
