package com.gylgroup.conelalma.repositories;

import com.gylgroup.conelalma.entities.Bebida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BebidaRepository extends JpaRepository<Bebida,Integer> {


    Optional<Bebida> findBebidaByNombre(String nombre);

    @Query(value = "SELECT * FROM Bebida WHERE estado=true", nativeQuery = true)
    List<Bebida> findAllByEstado();

}
