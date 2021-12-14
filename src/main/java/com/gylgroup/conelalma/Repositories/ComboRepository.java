
package com.gylgroup.conelalma.repositories;

import com.gylgroup.conelalma.entities.Combos;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ComboRepository extends JpaRepository<Combos,Integer> {
    @Query("SELECT c FROM Combos c WHERE c.estado = 1")
    List<Combos> findAllAndEstado();
    
    @Modifying
    @Query(value = "UPDATE Combos SET estado = true WHERE id = ?1", nativeQuery = true)
    void enable(Integer id);
    
    @Modifying
    @Query(value = "UPDATE Combos SET estado = false WHERE id = ?1", nativeQuery = true)
    void disable(Integer id);
    
    @Override
    Optional<Combos> findById(Integer Id);
    
    boolean existsById(Integer Id);
}
