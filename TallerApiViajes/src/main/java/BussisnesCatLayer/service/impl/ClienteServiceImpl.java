package BussisnesCatLayer.service.impl;

import BussisnesCatLayer.dto.ClienteDTO;
import BussisnesCatLayer.exception.BusinessValidationException;
import BussisnesCatLayer.exception.ResourceNotFoundException;
import BussisnesCatLayer.service.ClienteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import persistanceLayer.entity.ClienteEntity;
import persistanceLayer.mapper.mapper;
import persistanceLayer.repository.ClienteRepositoy;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepositoy clienteRepositoy;
    private final mapper mapper;

    @Override
    public ClienteDTO crearCliente(ClienteDTO clienteDTO) {
        log.info("Creando cliente con email: {}", clienteDTO.getEmail());

        clienteRepositoy.findByEmailIgnoreCase(clienteDTO.getEmail())
                .ifPresent(existente -> {
                    throw new BusinessValidationException(
                            "Ya existe un cliente registrado con el email: " + clienteDTO.getEmail());
                });

        ClienteEntity entity = mapper.toEntity(clienteDTO);
        entity.setIdCliente(null);
        ClienteEntity guardado = clienteRepositoy.save(entity);

        log.info("Cliente creado con ID: {}", guardado.getIdCliente());
        return mapper.toDto(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteDTO obtenerClientePorId(Long id) {
        ClienteEntity entity = buscarClienteOrThrow(id);
        return mapper.toDto(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClienteDTO> obtenerTodosLosClientes() {
        return clienteRepositoy.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public ClienteDTO actualizarCliente(Long id, ClienteDTO clienteDTO) {
        ClienteEntity existente = buscarClienteOrThrow(id);

        clienteRepositoy.findByEmailIgnoreCase(clienteDTO.getEmail())
                .filter(otro -> !otro.getIdCliente().equals(id))
                .ifPresent(otro -> {
                    throw new BusinessValidationException(
                            "Ya existe otro cliente registrado con el email: " + clienteDTO.getEmail());
                });

        existente.setNombre(clienteDTO.getNombre());
        existente.setEmail(clienteDTO.getEmail());
        existente.setDireccion(clienteDTO.getDireccion());

        ClienteEntity actualizado = clienteRepositoy.save(existente);
        log.info("Cliente actualizado ID: {}", id);
        return mapper.toDto(actualizado);
    }

    @Override
    public void eliminarCliente(Long id) {
        ClienteEntity existente = buscarClienteOrThrow(id);
        clienteRepositoy.delete(existente);
        log.info("Cliente eliminado ID: {}", id);
    }

    private ClienteEntity buscarClienteOrThrow(Long id) {
        return clienteRepositoy.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + id));
    }
}
