package BussisnesCatLayer.service;

import BussisnesCatLayer.dto.ViajeDTO;

import java.util.List;

/**
 * Logica de negocio para la gestion de viajes.
 */
public interface ViajeService {

    ViajeDTO crearViaje(ViajeDTO viajeDTO);

    ViajeDTO obtenerViajePorId(Long id);

    List<ViajeDTO> obtenerTodosLosViajes();

    ViajeDTO actualizarViaje(Long id, ViajeDTO viajeDTO);

    void eliminarViaje(Long id);
}