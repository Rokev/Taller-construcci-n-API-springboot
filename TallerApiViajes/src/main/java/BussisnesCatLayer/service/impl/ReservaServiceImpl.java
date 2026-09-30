package BussisnesCatLayer.service.impl;

import BussisnesCatLayer.dto.ReservaDTO;
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
    public ReservaDTO obtenerReservaPorId(Long id) throws Exception {
        return reservaDAO.findById(id)
                .orElseThrow(() -> new Exception("Reserva no encontrada con ID: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservaDTO> obtenerTodasLasReservas() {
        return reservaDAO.findAll();
    }

    @Override
    public ReservaDTO actualizarReserva(Long id, ReservaDTO reservaDTO) throws Exception {
        if (reservaDAO.findById(id)== null) {
            new Exception("Reserva no encontrada con ID: " + id);
        }
        validarViajeExiste(reservaDTO.getIdViaje());
        validarClienteExiste(reservaDTO.getIdCliente());
        validarFecha(reservaDTO.getFecha());
        validarEstado(reservaDTO.getEstado());

        ReservaDTO actualizada = reservaDAO.update(id, reservaDTO)
                .orElseThrow(() -> new Exception("Reserva no encontrada con ID: " + id));
        log.info("Reserva actualizada ID: {}", id);
        return actualizada;
    }

    @Override
    public void eliminarReserva(Long id) {
        if (!reservaDAO.deleteById(id)) {
            try {
                throw new Exception("Reserva no encontrada con ID: " + id);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        log.info("Reserva eliminada ID: {}", id);
    }

    private void validarViajeExiste(Long idViaje) {
        if (!viajeDAO.existsById(idViaje)) {
            try {
                throw new Exception(
                        "No se puede reservar: el viaje con ID " + idViaje + " no existe");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void validarClienteExiste(Long idCliente) {
        if (!clienteDAO.existsById(idCliente)) {
            try {
                throw new Exception(
                        "No se puede reservar: el cliente con ID " + idCliente + " no existe");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void validarFecha(LocalDateTime fecha) {
        if (fecha == null) {
            try {
                throw new Exception("La fecha de la reserva es obligatoria");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        if (fecha.isBefore(LocalDateTime.now())) {
            try {
                throw new Exception("La fecha de la reserva no puede ser en el pasado");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void validarEstado(String estado) {
        if (estado == null || !ESTADOS_VALIDOS.contains(estado.toUpperCase())) {
            try {
                throw new Exception(
                        "El estado de la reserva debe ser uno de: " + ESTADOS_VALIDOS);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
