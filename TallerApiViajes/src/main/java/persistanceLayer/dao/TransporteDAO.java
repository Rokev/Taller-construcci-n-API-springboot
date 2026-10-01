package persistanceLayer.dao;

import BussisnesCatLayer.dto.TransporteDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import persistanceLayer.entity.TransporteEntity;
import persistanceLayer.entity.ViajeEntity;
import persistanceLayer.mapper.mapper;
import persistanceLayer.repository.TransporteRepository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TransporteDAO {

    private final TransporteRepository transporteRepository;
    private final mapper mapper;

    public TransporteDTO save(TransporteDTO dto) {
        TransporteEntity entity = mapper.transporteToEntity(dto);
        entity.setIdTransporte(null);
        entity.setViaje(referenciaViaje(dto.getIdViaje()));
        TransporteEntity guardado = transporteRepository.save(entity);
        return mapper.transporteToDto(guardado);
    }

    public Optional<TransporteDTO> findById(Long id) {
        return transporteRepository.findById(id).map(mapper::transporteToDto);
    }

    public List<TransporteDTO> findAll() {
        return transporteRepository.findAll().stream().map(mapper::transporteToDto).toList();
    }

    public Optional<TransporteDTO> update(Long id, TransporteDTO dto) {
        return transporteRepository.findById(id).map(existente -> {
            existente.setCompania(dto.getCompania());
            existente.setHorario(dto.getHorario());
            existente.setDuracion(dto.getDuracion());
            existente.setClaseServicio(dto.getClaseServicio());
            existente.setViaje(referenciaViaje(dto.getIdViaje()));
            return mapper.transporteToDto(transporteRepository.save(existente));
        });
    }

    public boolean existsById(Long id) {
        return transporteRepository.existsById(id);
    }

    public boolean deleteById(Long id) {
        if (transporteRepository.existsById(id)) {
            transporteRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private ViajeEntity referenciaViaje(Long idViaje) {
        ViajeEntity referencia = new ViajeEntity();
        referencia.setIdViaje(idViaje);
        return referencia;
    }
}