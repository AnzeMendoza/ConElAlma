package com.gylgroup.conelalma.repositories;

import com.gylgroup.conelalma.entities.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario,Integer> {

    @Modifying
    @Query(value = "UPDATE comentario SET estado = true WHERE id = ?1", nativeQuery = true)
    void enable(Integer id);

    @Modifying
    @Query(value = "UPDATE comentario SET estado = false WHERE id = ?1", nativeQuery = true)
    void disable(Integer id);

    @Query(value = "SELECT * FROM comentario WHERE estado=true", nativeQuery = true)
    List<Comentario> findAllEnable();

    @Query(value = "SELECT * FROM comentario WHERE usuario_id = ?1 ",nativeQuery = true)
    List<Comentario> findByUsuarioId(Integer usuario_id);

    @Query(value = "SELECT * FROM comentario WHERE reserva_id = ?1 ",nativeQuery = true)
    List<Comentario> findByReserva(Integer reserva_id);

    Optional<Comentario> findById(Integer id);

    Boolean existsComentarioById(Integer id);
}
