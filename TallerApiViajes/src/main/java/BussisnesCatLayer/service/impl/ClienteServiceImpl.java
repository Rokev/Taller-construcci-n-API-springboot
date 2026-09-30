package BussisnesCatLayer.service.impl;

import BussisnesCatLayer.dto.ClienteDTO;
import BussisnesCatLayer.service.ClienteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import persistanceLayer.dao.ClienteDAO;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ClienteServiceImpl implements ClienteService {

    private final ClienteDAO clienteDAO;

    @Override
    public ClienteDTO crearCliente(ClienteDTO clienteDTO) {
        log.info("Creando cliente con email: {}", clienteDTO.getEmail());

        clienteDAO.findByEmail(clienteDTO.getEmail())
                .ifPresent(existente -> {
                    try {
                        throw new Exception(
                                "Ya existe un cliente registrado con el email: " + clienteDTO.getEmail());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });

        ClienteDTO guardado = clienteDAO.save(clienteDTO);
        log.info("Cliente creado con ID: {}", guardado.getIdCliente());
        return guardado;
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteDTO obtenerClientePorId(Long id) {
        return clienteDAO.findById(id).orElseThrow(() -> {
            try {
                throw new Exception("Cliente no encontrado con ID: " + id);
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClienteDTO> obtenerTodosLosClientes() {
        return clienteDAO.findAll();
    }

    @Override
    public ClienteDTO actualizarCliente(Long id, ClienteDTO clienteDTO) {
        if (!clienteDAO.existsById(id)) {
            try {
                throw new Exception("Cliente no encontrado con ID: " + id);
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        };

        clienteDAO.findByEmail(clienteDTO.getEmail())
                .filter(otro -> !otro.getIdCliente().equals(id))
                .ifPresent(otro -> {
                    try {
                        throw new Exception("Ya existe otro cliente registrado con el email: " + clienteDTO.getEmail());
                    }catch (Exception e){
                        throw new RuntimeException(e);
                    }
                });

        ClienteDTO actualizado = clienteDAO.update(id, clienteDTO)
                .orElseThrow(() ->{
                    try {
                        throw new Exception("Cliente no encontrado con ID: " + id);
                    }catch (Exception e){
                        throw new RuntimeException(e);
                    }
                });
            log.info("Cliente actualizado ID: {}", id);
            return actualizado;
    }

    @Override
    public void eliminarCliente(Long id) {
        if (!clienteDAO.deleteById(id)) {
            try {
                throw new Exception("Cliente no encontrado con ID: " + id);
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        };
        log.info("Cliente eliminado ID: {}", id);
    }
}
