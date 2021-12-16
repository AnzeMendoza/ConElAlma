
package com.gylgroup.conelalma.repositories;

import com.gylgroup.conelalma.entities.Combo;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ComboRepository extends JpaRepository<Combo,Integer> {
    @Query(value = "SELECT * FROM combos WHERE estado = 1", nativeQuery = true)
    List<Combo> findAllByEstado();
}
