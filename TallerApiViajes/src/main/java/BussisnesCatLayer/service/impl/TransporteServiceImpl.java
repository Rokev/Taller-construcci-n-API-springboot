package BussisnesCatLayer.service.impl;

import BussisnesCatLayer.dto.TransporteDTO;
import BussisnesCatLayer.exception.BusinessValidationException;
import BussisnesCatLayer.exception.ResourceNotFoundException;
import BussisnesCatLayer.service.TransporteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import persistanceLayer.entity.Transporte;
import persistanceLayer.entity.ViajeEntity;
import persistanceLayer.mapper.mapper;
import persistanceLayer.repository.TransporteRepository;
import persistanceLayer.repository.ViajeRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class TransporteServiceImpl implements TransporteService {

    private final TransporteRepository transporteRepository;
    private final ViajeRepository viajeRepository;
    private final mapper mapper;

    @Override
    public TransporteDTO crearTransporte(TransporteDTO transporteDTO) {
        log.info("Creando transporte para el viaje ID: {}", transporteDTO.getIdViaje());

        ViajeEntity viaje = buscarViajeOrThrow(transporteDTO.getIdViaje());

        Transporte entity = mapper.toEntity(transporteDTO);
        entity.setIdTransporte(null);
        entity.setViaje(viaje);

        Transporte guardado = transporteRepository.save(entity);
        log.info("Transporte creado con ID: {}", guardado.getIdTransporte());
        return mapper.toDto(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public TransporteDTO obtenerTransportePorId(Long id) {
        return mapper.toDto(buscarTransporteOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransporteDTO> obtenerTodosLosTransportes() {
        return transporteRepository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public TransporteDTO actualizarTransporte(Long id, TransporteDTO transporteDTO) {
        Transporte existente = buscarTransporteOrThrow(id);
        ViajeEntity viaje = buscarViajeOrThrow(transporteDTO.getIdViaje());

        existente.setCompania(transporteDTO.getCompania());
        existente.setHorario(transporteDTO.getHorario());
        existente.setDuracion(transporteDTO.getDuracion());
        existente.setClaseServicio(transporteDTO.getClaseServicio());
        existente.setViaje(viaje);

        Transporte actualizado = transporteRepository.save(existente);
        log.info("Transporte actualizado ID: {}", id);
        return mapper.toDto(actualizado);
    }

    @Override
    public void eliminarTransporte(Long id) {
        Transporte existente = buscarTransporteOrThrow(id);
        transporteRepository.delete(existente);
        log.info("Transporte eliminado ID: {}", id);
    }

    private Transporte buscarTransporteOrThrow(Long id) {
        return transporteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transporte no encontrado con ID: " + id));
    }

    private ViajeEntity buscarViajeOrThrow(Long idViaje) {
        return viajeRepository.findById(idViaje)
                .orElseThrow(() -> new BusinessValidationException(
                        "No se puede asignar el transporte: el viaje con ID " + idViaje + " no existe"));
    }
}
