package com.gylgroup.conelalma.repositories;

import com.gylgroup.conelalma.entities.Bebida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BebidaRepository extends JpaRepository<Bebida,Integer> {


    boolean existsBebidaByNombre(String nombre);

    List<Bebida>findAllByEstado(@Param("estado") Boolean estado);


}
