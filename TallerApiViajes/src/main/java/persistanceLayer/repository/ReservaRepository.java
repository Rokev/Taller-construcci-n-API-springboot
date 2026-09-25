package persistanceLayer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import persistanceLayer.entity.ClienteEntity;
import persistanceLayer.entity.ReservaEntity;
import persistanceLayer.entity.ViajeEntity;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<ReservaEntity, Long> {

    default ReservaEntity crearReserva(ViajeEntity viaje, ClienteEntity cliente,
                                       LocalDateTime fecha, String estado,
                                       int numeroPersonas) {
        ReservaEntity reserva = new ReservaEntity();
        reserva.setViaje(viaje);
        reserva.setCliente(cliente);
        reserva.setFecha(fecha);
        reserva.setEstado(estado);
        reserva.setNumeroPersonas(numeroPersonas);
        return save(reserva);
    }

    List<ReservaEntity> findByClienteIdClienteOrderByFechaDesc(Long idCliente);

    List<ReservaEntity> findByViajeIdViaje(Long idViaje);

    List<ReservaEntity> findByEstadoIgnoreCase(String estado);

    boolean existsByIdReservaAndClienteIdCliente(Long idReserva, Long idCliente);

    @Modifying
    @Query("UPDATE ReservaEntity r SET r.estado = 'CANCELADA' WHERE r.idReserva = :idReserva")
    int cancelarReserva(@Param("idReserva") Long idReserva);
}
