package persistanceLayer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import persistanceLayer.entity.Transporte;

import java.util.List;

@Repository
public interface TransporteRepository extends JpaRepository<Transporte, Long> {

	List<Transporte> findByViajeIdViaje(Long idViaje);
}
