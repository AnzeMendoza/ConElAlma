package com.gylgroup.conelalma.repositories;

import com.gylgroup.conelalma.entities.Comida;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ComidaRepository extends JpaRepository<Comida,Integer> {     
     
    @Query("SELECT c FROM Comida c WHERE c.estado = 1")
    List<Comida> findAllAndEstado();
    
    @Modifying
    @Query(value = "UPDATE Comida SET estado = true WHERE id = ?1", nativeQuery = true)
    void enable(Integer id);
    
    @Modifying
    @Query(value = "UPDATE Comida SET estado = false WHERE id = ?1", nativeQuery = true)
    void disable(Integer id);
    
}
