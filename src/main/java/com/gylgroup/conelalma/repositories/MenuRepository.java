package com.gylgroup.conelalma.repositories;

import com.gylgroup.conelalma.entities.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuRepository extends JpaRepository<Menu,Integer> {
    // LA BUSQUEDA COMPLETA DE MENU EN LISTA se resuelve con findAll() en el service
    // LA MODIFICACION DE MENU se resuelve con el repository.save en el service

    @Query(value = "SELECT * FROM menu WHERE cantidad_base_comensales= ?1",nativeQuery = true)
    List<Menu> listaMenuXComensales(Integer cantBaseComensales);

    Optional<Menu> findById(Integer id);

    Boolean existsMenuBy(Integer id);

    @Query(value = "SELECT * FROM menu WHERE estado=true", nativeQuery = true)
    List<Menu> obtenerAltas();


    @Modifying
    @Query(value = "UPDATE menu SET estado = true WHERE id = ?1", nativeQuery = true)
    void darAltaMenu(Integer id);
}
