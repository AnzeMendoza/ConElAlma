package com.gylgroup.conelalma.repositorys;

import com.gylgroup.conelalma.entities.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva,Integer> {

    @Modifying
    @Query(value="UPDATE reserva  SET presupuesto_live_id = ?1 , fecha_reserva= ?2, estado = ?3, tipo_de_pago = ?4 , pago_efectuado = ?5 WHERE id = ?6 ",nativeQuery = true)
    void modificarReserva(Integer presupuesto_id, Date fecha_reserva, Boolean estado, String tipo_pago, Boolean pego_efectuado, Integer id);

    @Modifying
    @Query(value = "UPDATE reserva SET estado = true WHERE id = ?1", nativeQuery = true)
    void habilitar(Integer id);

    @Query(value = "SELECT * FROM reserva WHERE estado=true ORDER BY fecha_reserva", nativeQuery = true)
    List<Reserva> traerAltas();

    @Query(value = "SELECT * FROM reserva WHERE fecha_reserva = ?1 ORDER BY fecha_reserva",nativeQuery = true)
    List<Reserva> traerPorFecha(Date fecha);

    Optional<Reserva> findById(Integer id);

    Boolean existsReservaById(Integer id);
}
