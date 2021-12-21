package com.gylgroup.conelalma.repositories;

import com.gylgroup.conelalma.entities.Comentario;
import com.gylgroup.conelalma.entities.PresupuestoLive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PresupuestoLiveRepository extends JpaRepository<PresupuestoLive, Integer> {
    boolean existsById(Integer id);

    List<PresupuestoLive> findByEstadoTrue();

    @Query(value = "SELECT * FROM presupuesto_live WHERE usuario_id = ?1 " +
            "ORDER BY fecha_presupuesto DESC LIMIT 1",nativeQuery = true)
    PresupuestoLive findByIdUsuario(Integer idUsuario);
//SELECT  *  FROM presupuesto_live ORDER BY fecha_presupuesto DESC LIMIT 1;
}
