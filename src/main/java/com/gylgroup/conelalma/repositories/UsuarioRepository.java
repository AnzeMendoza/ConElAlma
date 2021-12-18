package com.gylgroup.conelalma.repositories;

import java.util.List;
import java.util.Optional;

import com.gylgroup.conelalma.entities.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByEmail(String email);

    @Query(value = "SELECT * FROM usuario WHERE estado = :estado", nativeQuery = true)
    List<Usuario> findAllByEstado(boolean estado);
 }
