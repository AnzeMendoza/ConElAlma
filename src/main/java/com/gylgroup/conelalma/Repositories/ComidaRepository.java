package com.gylgroup.conelalma.repositories;

import com.gylgroup.conelalma.entities.Comida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComidaRepository extends JpaRepository<Comida,Integer> {
    boolean existsById(int id);
    
    @Query(value = "SELECT * FROM comida WHERE estado = 1", nativeQuery = true)
    List<Comida> findAllByEstado();
}
