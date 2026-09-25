package BussisnesCatLayer.service.impl;

import BussisnesCatLayer.dto.ReservaDTO;
import BussisnesCatLayer.exception.BusinessValidationException;
import BussisnesCatLayer.exception.ResourceNotFoundException;
import BussisnesCatLayer.service.ReservaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import persistanceLayer.dao.ClienteDAO;
import persistanceLayer.dao.ReservaDAO;
import persistanceLayer.dao.ViajeDAO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ReservaServiceImpl implements ReservaService {

    private static final Set<String> ESTADOS_VALIDOS = Set.of("ACTIVA", "PENDIENTE", "CANCELADA");

    private final ReservaDAO reservaDAO;
    private final ViajeDAO viajeDAO;
    private final ClienteDAO clienteDAO;

    @Override
    public ReservaDTO crearReserva(ReservaDTO reservaDTO) {
        log.info("Creando reserva para viaje ID: {} y cliente ID: {}",
                reservaDTO.getIdViaje(), reservaDTO.getIdCliente());

        validarViajeExiste(reservaDTO.getIdViaje());
        validarClienteExiste(reservaDTO.getIdCliente());
        validarFecha(reservaDTO.getFecha());
        validarEstado(reservaDTO.getEstado());

        ReservaDTO guardada = reservaDAO.save(reservaDTO);
        log.info("Reserva creada con ID: {}", guardada.getIdReserva());
        return guardada;
    }

    @Override
    @Transactional(readOnly = true)
    public ReservaDTO obtenerReservaPorId(Long id) {
        return reservaDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada con ID: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservaDTO> obtenerTodasLasReservas() {
        return reservaDAO.findAll();
    }

    @Override
    public ReservaDTO actualizarReserva(Long id, ReservaDTO reservaDTO) {
        if (!reservaDAO.existsById(id)) {
            throw new ResourceNotFoundException("Reserva no encontrada con ID: " + id);
        }
        validarViajeExiste(reservaDTO.getIdViaje());
        validarClienteExiste(reservaDTO.getIdCliente());
        validarFecha(reservaDTO.getFecha());
        validarEstado(reservaDTO.getEstado());

        ReservaDTO actualizada = reservaDAO.update(id, reservaDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada con ID: " + id));
        log.info("Reserva actualizada ID: {}", id);
        return actualizada;
    }

    @Override
    public void eliminarReserva(Long id) {
        if (!reservaDAO.deleteById(id)) {
            throw new ResourceNotFoundException("Reserva no encontrada con ID: " + id);
        }
        log.info("Reserva eliminada ID: {}", id);
    }

    private void validarViajeExiste(Long idViaje) {
        if (!viajeDAO.existsById(idViaje)) {
            throw new BusinessValidationException(
                    "No se puede reservar: el viaje con ID " + idViaje + " no existe");
        }
    }

    private void validarClienteExiste(Long idCliente) {
        if (!clienteDAO.existsById(idCliente)) {
            throw new BusinessValidationException(
                    "No se puede reservar: el cliente con ID " + idCliente + " no existe");
        }
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
