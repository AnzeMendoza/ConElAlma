
package com.gylgroup.conelalma.repositories;

import com.gylgroup.conelalma.entities.Cupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;


@Controller
public interface CuponRepository extends JpaRepository<Cupon, Integer>{
    
}
