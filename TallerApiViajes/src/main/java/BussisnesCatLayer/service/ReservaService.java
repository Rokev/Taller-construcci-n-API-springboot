package BussisnesCatLayer.service;

import BussisnesCatLayer.dto.ReservaDTO;

import java.util.List;


public interface ReservaService {

    ReservaDTO crearReserva(ReservaDTO reservaDTO);

    ReservaDTO obtenerReservaPorId(Long id);

    List<ReservaDTO> obtenerTodasLasReservas();

    ReservaDTO actualizarReserva(Long id, ReservaDTO reservaDTO);

    void eliminarReserva(Long id);
}