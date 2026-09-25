package BussisnesCatLayer.service;

import BussisnesCatLayer.dto.ClienteDTO;

import java.util.List;

/**
 * Logica de negocio para la gestion de clientes.
 */
public interface ClienteService {

    ClienteDTO crearCliente(ClienteDTO clienteDTO);

    ClienteDTO obtenerClientePorId(Long id);

    List<ClienteDTO> obtenerTodosLosClientes();

    ClienteDTO actualizarCliente(Long id, ClienteDTO clienteDTO);

    void eliminarCliente(Long id);
}