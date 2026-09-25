package persistanceLayer.dao;

import BussisnesCatLayer.dto.ClienteDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import persistanceLayer.entity.ClienteEntity;
import persistanceLayer.mapper.mapper;
import persistanceLayer.repository.ClienteRepositoy;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ClienteDAO {

    private final ClienteRepositoy clienteRepositoy;
    private final mapper mapper;

    public ClienteDTO save(ClienteDTO dto) {
        ClienteEntity entity = mapper.toEntity(dto);
        entity.setIdCliente(null);
        ClienteEntity guardado = clienteRepositoy.save(entity);
        return mapper.toDto(guardado);
    }

    public Optional<ClienteDTO> findById(Long id) {
        return clienteRepositoy.findById(id).map(mapper::toDto);
    }

    public List<ClienteDTO> findAll() {
        return clienteRepositoy.findAll().stream().map(mapper::toDto).toList();
    }

    public Optional<ClienteDTO> update(Long id, ClienteDTO dto) {
        return clienteRepositoy.findById(id).map(existente -> {
            existente.setNombre(dto.getNombre());
            existente.setEmail(dto.getEmail());
            existente.setDireccion(dto.getDireccion());
            return mapper.toDto(clienteRepositoy.save(existente));
        });
    }

    public boolean deleteById(Long id) {
        if (clienteRepositoy.existsById(id)) {
            clienteRepositoy.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean existsById(Long id) {
        return clienteRepositoy.existsById(id);
    }

    public Optional<ClienteDTO> findByEmail(String email) {
        return clienteRepositoy.findByEmailIgnoreCase(email).map(mapper::toDto);
    }
}