package com.gylgroup.conelalma.repositories;

import com.gylgroup.conelalma.entities.Comida;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ComidaRepository extends JpaRepository<Comida,Integer> {

    //Todo usar el que ya viene jpa, porque hay dos?
    @Query("SELECT c FROM Comida c WHERE c.id =: id")
    Comida findbyId(@Param("id") Integer id);
    
    Comida findById();
   
    @Override
    List<Comida> findAll();
   
    boolean existsById();
}
