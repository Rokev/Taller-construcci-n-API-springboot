package BussisnesCatLayer.service.impl;

import BussisnesCatLayer.dto.ReservaDTO;
import BussisnesCatLayer.exception.BusinessValidationException;
import BussisnesCatLayer.exception.ResourceNotFoundException;
import BussisnesCatLayer.service.ReservaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import persistanceLayer.entity.ClienteEntity;
import persistanceLayer.entity.ReservaEntity;
import persistanceLayer.entity.ViajeEntity;
import persistanceLayer.mapper.mapper;
import persistanceLayer.repository.ClienteRepositoy;
import persistanceLayer.repository.ReservaRepository;
import persistanceLayer.repository.ViajeRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ReservaServiceImpl implements ReservaService {

    private static final Set<String> ESTADOS_VALIDOS = Set.of("ACTIVA", "PENDIENTE", "CANCELADA");

    private final ReservaRepository reservaRepository;
    private final ViajeRepository viajeRepository;
    private final ClienteRepositoy clienteRepositoy;
    private final mapper mapper;

    @Override
    public ReservaDTO crearReserva(ReservaDTO reservaDTO) {
        log.info("Creando reserva para viaje ID: {} y cliente ID: {}",
                reservaDTO.getIdViaje(), reservaDTO.getIdCliente());

        ViajeEntity viaje = buscarViajeOrThrow(reservaDTO.getIdViaje());
        ClienteEntity cliente = buscarClienteOrThrow(reservaDTO.getIdCliente());
        validarFecha(reservaDTO.getFecha());
        validarEstado(reservaDTO.getEstado());

        ReservaEntity entity = mapper.toEntity(reservaDTO);
        entity.setIdReserva(null);
        entity.setViaje(viaje);
        entity.setCliente(cliente);
        entity.setEstado(reservaDTO.getEstado().toUpperCase());

        ReservaEntity guardada = reservaRepository.save(entity);
        log.info("Reserva creada con ID: {}", guardada.getIdReserva());
        return mapper.toDto(guardada);
    }

    @Override
    @Transactional(readOnly = true)
    public ReservaDTO obtenerReservaPorId(Long id) {
        return mapper.toDto(buscarReservaOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservaDTO> obtenerTodasLasReservas() {
        return reservaRepository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public ReservaDTO actualizarReserva(Long id, ReservaDTO reservaDTO) {
        ReservaEntity existente = buscarReservaOrThrow(id);
        ViajeEntity viaje = buscarViajeOrThrow(reservaDTO.getIdViaje());
        ClienteEntity cliente = buscarClienteOrThrow(reservaDTO.getIdCliente());
        validarFecha(reservaDTO.getFecha());
        validarEstado(reservaDTO.getEstado());

        existente.setFecha(reservaDTO.getFecha());
        existente.setEstado(reservaDTO.getEstado().toUpperCase());
        existente.setNumeroPersonas(reservaDTO.getNumeroPersonas());
        existente.setViaje(viaje);
        existente.setCliente(cliente);

        ReservaEntity actualizada = reservaRepository.save(existente);
        log.info("Reserva actualizada ID: {}", id);
        return mapper.toDto(actualizada);
    }

    @Override
    public void eliminarReserva(Long id) {
        ReservaEntity existente = buscarReservaOrThrow(id);
        reservaRepository.delete(existente);
        log.info("Reserva eliminada ID: {}", id);
    }

    private ReservaEntity buscarReservaOrThrow(Long id) {
        return reservaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada con ID: " + id));
    }

    private ViajeEntity buscarViajeOrThrow(Long idViaje) {
        return viajeRepository.findById(idViaje)
                .orElseThrow(() -> new BusinessValidationException(
                        "No se puede reservar: el viaje con ID " + idViaje + " no existe"));
    }

    private ClienteEntity buscarClienteOrThrow(Long idCliente) {
        return clienteRepositoy.findById(idCliente)
                .orElseThrow(() -> new BusinessValidationException(
                        "No se puede reservar: el cliente con ID " + idCliente + " no existe"));
    }

    private void validarFecha(LocalDateTime fecha) {
        if (fecha == null) {
            throw new BusinessValidationException("La fecha de la reserva es obligatoria");
        }
        if (fecha.isBefore(LocalDateTime.now())) {
            throw new BusinessValidationException("La fecha de la reserva no puede ser en el pasado");
        }
    }

    private void validarEstado(String estado) {
        if (estado == null || !ESTADOS_VALIDOS.contains(estado.toUpperCase())) {
            throw new BusinessValidationException(
                    "El estado de la reserva debe ser uno de: " + ESTADOS_VALIDOS);
        }
    }
}