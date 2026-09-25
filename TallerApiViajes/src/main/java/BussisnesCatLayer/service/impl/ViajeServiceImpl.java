package BussisnesCatLayer.service.impl;

import BussisnesCatLayer.dto.ViajeDTO;
import BussisnesCatLayer.exception.ResourceNotFoundException;
import BussisnesCatLayer.service.ViajeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import persistanceLayer.entity.ViajeEntity;
import persistanceLayer.mapper.mapper;
import persistanceLayer.repository.ViajeRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ViajeServiceImpl implements ViajeService {

    private final ViajeRepository viajeRepository;
    private final mapper mapper;

    @Override
    public ViajeDTO crearViaje(ViajeDTO viajeDTO) {
        log.info("Creando viaje con destino: {}", viajeDTO.getDestino());

        ViajeEntity entity = mapper.toEntity(viajeDTO);
        entity.setIdViaje(null);
        ViajeEntity guardado = viajeRepository.save(entity);

        log.info("Viaje creado con ID: {}", guardado.getIdViaje());
        return mapper.toDto(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public ViajeDTO obtenerViajePorId(Long id) {
        return mapper.toDto(buscarViajeOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ViajeDTO> obtenerTodosLosViajes() {
        return viajeRepository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public ViajeDTO actualizarViaje(Long id, ViajeDTO viajeDTO) {
        ViajeEntity existente = buscarViajeOrThrow(id);

        existente.setDestino(viajeDTO.getDestino());
        existente.setDuracionDias(viajeDTO.getDuracionDias());
        existente.setPrecio(viajeDTO.getPrecio());
        existente.setFechasDisponibles(viajeDTO.getFechasDisponibles());
        existente.setDescripcion(viajeDTO.getDescripcion());

        ViajeEntity actualizado = viajeRepository.save(existente);
        log.info("Viaje actualizado ID: {}", id);
        return mapper.toDto(actualizado);
    }

    @Override
    public void eliminarViaje(Long id) {
        ViajeEntity existente = buscarViajeOrThrow(id);
        viajeRepository.delete(existente);
        log.info("Viaje eliminado ID: {}", id);
    }

    private ViajeEntity buscarViajeOrThrow(Long id) {
        return viajeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Viaje no encontrado con ID: " + id));
    }
}