package persistanceLayer.dao;

import BussisnesCatLayer.dto.ViajeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import persistanceLayer.entity.ViajeEntity;
import persistanceLayer.mapper.mapper;
import persistanceLayer.repository.ViajeRepository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ViajeDAO {

    private final ViajeRepository viajeRepository;
    private final mapper mapper;

    public ViajeDTO save(ViajeDTO dto) {
        ViajeEntity entity = mapper.toEntity(dto);
        entity.setIdViaje(null);
        ViajeEntity guardado = viajeRepository.save(entity);
        return mapper.toDto(guardado);
    }

    public Optional<ViajeDTO> findById(Long id) {
        return viajeRepository.findById(id).map(mapper::toDto);
    }

    public List<ViajeDTO> findAll() {
        return viajeRepository.findAll().stream().map(mapper::toDto).toList();
    }

    public Optional<ViajeDTO> update(Long id, ViajeDTO dto) {
        return viajeRepository.findById(id).map(existente -> {
            existente.setDestino(dto.getDestino());
            existente.setDuracionDias(dto.getDuracionDias());
            existente.setPrecio(dto.getPrecio());
            existente.setFechasDisponibles(dto.getFechasDisponibles());
            existente.setDescripcion(dto.getDescripcion());
            return mapper.toDto(viajeRepository.save(existente));
        });
    }

    public boolean deleteById(Long id) {
        if (viajeRepository.existsById(id)) {
            viajeRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean existsById(Long id) {
        return viajeRepository.existsById(id);
    }
}