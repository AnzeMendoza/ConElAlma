package com.gylgroup.conelalma.repositories;

import com.gylgroup.conelalma.entities.Bebida;
import com.gylgroup.conelalma.entities.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BebidaRepository extends JpaRepository<Bebida,Integer> {

    // LA BUSQUEDA COMPLETA DE BEBIDAS EN LISTA se resuelve con findAll() en el service
    // LA MODIFICACION DE BEBIDA se resuelve con el repository.save en el service

    @Query(value = "SELECT * FROM bebida WHERE cantidad_base_comensales= ?1",nativeQuery = true)
    List<Menu> listaMenuXComensales(Integer cantBaseComensales);

    Optional<Bebida> findById(Integer id);

    Boolean existsBebidaBy(Integer id);

    @Query(value = "SELECT * FROM bebida WHERE estado=true", nativeQuery = true)
    List<Menu> obtenerAltas();


    @Modifying
    @Query(value = "UPDATE bebida SET estado = true WHERE id = ?1", nativeQuery = true)
    void darAltaBebida(Integer id);

}
