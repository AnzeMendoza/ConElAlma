package com.gylgroup.conelalma.repositories;

import com.gylgroup.conelalma.entities.Local;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalRepository extends JpaRepository<Local,Integer> {
    @Query("SELECT l FROM Local l WHERE l.id =:id")
    Local BuscarLocalPorId(@Param("id")Integer id);  
    
    @Query("SELECT l FROM Local l WHERE l.precio <=: maximo")
    List<Local> BuscarPorPrecioMaximo(@Param("maximo") Double maximo);
    
    @Query("SELECT l FROM Local l WHERE l.estado = 1")
    List<Local> misLocales();
    
    @Query("SELECT l FROM Local l WHERE l.cantidadMaximaPersonas >=: maxima")
    List<Local> BuscarPorCantidadPersonas(@Param("maxima") Integer maxima);
    
    @Query("SELECT l FROM Local l WHERE l.direccion =: direccion")
    List<Local> BuscarPorDireccion(@Param("direccion") String direccion);
    
    @Modifying
    @Query(value = "UPDATE Local SET estado = true WHERE id = ?1", nativeQuery = true)
    void enable(Integer id);
    
    @Modifying
    @Query(value = "UPDATE Local SET estado = false WHERE id = ?1", nativeQuery = true)
    void disable(Integer id);
    boolean existsById(Integer Id);
}
