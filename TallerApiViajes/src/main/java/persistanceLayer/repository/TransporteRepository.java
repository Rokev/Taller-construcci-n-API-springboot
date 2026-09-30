package persistanceLayer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import persistanceLayer.entity.TransporteEntity;

import java.util.List;

@Repository
public interface TransporteRepository extends JpaRepository<TransporteEntity, Long> {

	List<TransporteEntity> findByViajeIdViaje(Long idViaje);
}
