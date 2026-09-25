package BussisnesCatLayer.service.impl;

import BussisnesCatLayer.dto.TransporteDTO;
import BussisnesCatLayer.exception.BusinessValidationException;
import BussisnesCatLayer.exception.ResourceNotFoundException;
import BussisnesCatLayer.service.TransporteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import persistanceLayer.dao.TransporteDAO;
import persistanceLayer.dao.ViajeDAO;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class TransporteServiceImpl implements TransporteService {

    private final TransporteDAO transporteDAO;
    private final ViajeDAO viajeDAO;

    @Override
    public TransporteDTO crearTransporte(TransporteDTO transporteDTO) {
        log.info("Creando transporte para el viaje ID: {}", transporteDTO.getIdViaje());

        validarViajeExiste(transporteDTO.getIdViaje());

        TransporteDTO guardado = transporteDAO.save(transporteDTO);
        log.info("Transporte creado con ID: {}", guardado.getIdTransporte());
        return guardado;
    }

    @Override
    @Transactional(readOnly = true)
    public TransporteDTO obtenerTransportePorId(Long id) {
        return transporteDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transporte no encontrado con ID: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransporteDTO> obtenerTodosLosTransportes() {
        return transporteDAO.findAll();
    }

    @Override
    public TransporteDTO actualizarTransporte(Long id, TransporteDTO transporteDTO) {
        if (!transporteDAO.existsById(id)) {
            throw new ResourceNotFoundException("Transporte no encontrado con ID: " + id);
        }
        validarViajeExiste(transporteDTO.getIdViaje());

        TransporteDTO actualizado = transporteDAO.update(id, transporteDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Transporte no encontrado con ID: " + id));
        log.info("Transporte actualizado ID: {}", id);
        return actualizado;
    }

    @Override
    public void eliminarTransporte(Long id) {
        if (!transporteDAO.deleteById(id)) {
            throw new ResourceNotFoundException("Transporte no encontrado con ID: " + id);
        }
        log.info("Transporte eliminado ID: {}", id);
    }

    private void validarViajeExiste(Long idViaje) {
        if (!viajeDAO.existsById(idViaje)) {
            throw new BusinessValidationException(
                    "No se puede asignar el transporte: el viaje con ID " + idViaje + " no existe");
        }
    }
}
