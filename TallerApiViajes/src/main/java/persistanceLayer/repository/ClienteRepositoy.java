package persistanceLayer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import persistanceLayer.entity.ClienteEntity;

import java.util.Optional;

@Repository
public interface ClienteRepositoy extends JpaRepository<ClienteEntity, Long> {

	Optional<ClienteEntity> findByEmailIgnoreCase(String email);
}
