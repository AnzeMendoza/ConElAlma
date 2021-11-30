package com.gylgroup.conelalma.repositories;

import com.gylgroup.conelalma.entities.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalRepository extends JpaRepository<Local,Integer> {
    @Query("SELECT l FROM Local l WHERE l.id =: id")
    Local BuscarLocalPorId(@Param("id")Integer Id);    
    boolean existsById(Integer Id);
    
}
