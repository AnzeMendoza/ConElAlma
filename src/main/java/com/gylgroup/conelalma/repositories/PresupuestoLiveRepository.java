package com.gylgroup.conelalma.repositories;

import com.gylgroup.conelalma.entities.PresupuestoLive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PresupuestoLiveRepository extends JpaRepository<PresupuestoLive, Integer> {
    boolean existsById(Integer id);
}
