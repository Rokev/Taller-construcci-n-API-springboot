package persistanceLayer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import persistanceLayer.entity.ViajeEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ViajeRepository extends JpaRepository<ViajeEntity, Long> {

	Optional<ViajeEntity> findByIdViaje(Long idViaje);

	List<ViajeEntity> findByFechasDisponiblesGreaterThanEqual(LocalDateTime fecha);

	List<ViajeEntity> findByDestinoContainingIgnoreCase(String destino);

	List<ViajeEntity> findByPrecioBetween(double precioMinimo, double precioMaximo);
}
