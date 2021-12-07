package com.gylgroup.conelalma.repositories;

import com.gylgroup.conelalma.entities.Rol;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {

}
