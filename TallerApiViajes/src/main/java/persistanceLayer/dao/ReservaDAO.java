package persistanceLayer.dao;

import BussisnesCatLayer.dto.ReservaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import persistanceLayer.entity.ClienteEntity;
import persistanceLayer.entity.ReservaEntity;
import persistanceLayer.entity.ViajeEntity;
import persistanceLayer.mapper.mapper;
import persistanceLayer.repository.ReservaRepository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ReservaDAO {

    private final ReservaRepository reservaRepository;
    private final mapper mapper;

    public ReservaDTO save(ReservaDTO dto) {
        ReservaEntity entity = mapper.toEntity(dto);
        entity.setIdReserva(null);
        entity.setViaje(referenciaViaje(dto.getIdViaje()));
        entity.setCliente(referenciaCliente(dto.getIdCliente()));
        entity.setEstado(dto.getEstado().toUpperCase());

        ReservaEntity guardada = reservaRepository.save(entity);
        return mapper.toDto(guardada);
    }

    public Optional<ReservaDTO> findById(Long id) {
        return reservaRepository.findById(id).map(mapper::toDto);
    }

    public List<ReservaDTO> findAll() {
        return reservaRepository.findAll().stream().map(mapper::toDto).toList();
    }

    public Optional<ReservaDTO> update(Long id, ReservaDTO dto) {
        return reservaRepository.findById(id).map(existente -> {
            existente.setFecha(dto.getFecha());
            existente.setEstado(dto.getEstado().toUpperCase());
            existente.setNumeroPersonas(dto.getNumeroPersonas());
            existente.setViaje(referenciaViaje(dto.getIdViaje()));
            existente.setCliente(referenciaCliente(dto.getIdCliente()));
            return mapper.toDto(reservaRepository.save(existente));
        });
    }

    public boolean deleteById(Long id) {
        if (reservaRepository.existsById(id)) {
            reservaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private ViajeEntity referenciaViaje(Long idViaje) {
        ViajeEntity referencia = new ViajeEntity();
        referencia.setIdViaje(idViaje);
        return referencia;
    }

    private ClienteEntity referenciaCliente(Long idCliente) {
        ClienteEntity referencia = new ClienteEntity();
        referencia.setIdCliente(idCliente);
        return referencia;
    }
}