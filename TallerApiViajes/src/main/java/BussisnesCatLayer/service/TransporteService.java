package BussisnesCatLayer.service;

import BussisnesCatLayer.dto.TransporteDTO;

import java.util.List;

/**
 * Logica de negocio para la gestion de transportes.
 */
public interface TransporteService {

    TransporteDTO crearTransporte(TransporteDTO transporteDTO);

    TransporteDTO obtenerTransportePorId(Long id);

    List<TransporteDTO> obtenerTodosLosTransportes();

    TransporteDTO actualizarTransporte(Long id, TransporteDTO transporteDTO);

    void eliminarTransporte(Long id);
}