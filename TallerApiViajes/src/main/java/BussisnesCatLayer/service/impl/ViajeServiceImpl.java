package BussisnesCatLayer.service.impl;

import BussisnesCatLayer.dto.ViajeDTO;
import BussisnesCatLayer.exception.ResourceNotFoundException;
import BussisnesCatLayer.service.ViajeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import persistanceLayer.dao.ViajeDAO;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ViajeServiceImpl implements ViajeService {

    private final ViajeDAO viajeDAO;

    @Override
    public ViajeDTO crearViaje(ViajeDTO viajeDTO) {
        log.info("Creando viaje con destino: {}", viajeDTO.getDestino());

        ViajeDTO guardado = viajeDAO.save(viajeDTO);
        log.info("Viaje creado con ID: {}", guardado.getIdViaje());
        return guardado;
    }

    @Override
    @Transactional(readOnly = true)
    public ViajeDTO obtenerViajePorId(Long id) {
        return viajeDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Viaje no encontrado con ID: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ViajeDTO> obtenerTodosLosViajes() {
        return viajeDAO.findAll();
    }

    @Override
    public ViajeDTO actualizarViaje(Long id, ViajeDTO viajeDTO) {
        ViajeDTO actualizado = viajeDAO.update(id, viajeDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Viaje no encontrado con ID: " + id));
        log.info("Viaje actualizado ID: {}", id);
        return actualizado;
    }

    @Override
    public void eliminarViaje(Long id) {
        if (!viajeDAO.deleteById(id)) {
            throw new ResourceNotFoundException("Viaje no encontrado con ID: " + id);
        }
        log.info("Viaje eliminado ID: {}", id);
    }
}
