package com.gylgroup.conelalma.repositories;

import com.gylgroup.conelalma.entities.Comida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComidaRepository extends JpaRepository<Comida,Integer> {
    boolean existsById(int id);
}
