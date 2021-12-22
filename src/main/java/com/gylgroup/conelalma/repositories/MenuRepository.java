package com.gylgroup.conelalma.repositories;

import com.gylgroup.conelalma.entities.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Integer> {
    @Query(value = "SELECT * FROM Menu WHERE estado=:estado", nativeQuery = true)
    List<Menu> findAllByEstado(@Param("estado") boolean estado);

   // Optional<Menu> findMenuByNombre(String nombre);
}
