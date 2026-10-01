package persistanceLayer.mapper;
import BussisnesCatLayer.dto.ClienteDTO;
import BussisnesCatLayer.dto.ReservaDTO;
import BussisnesCatLayer.dto.TransporteDTO;
import BussisnesCatLayer.dto.ViajeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import persistanceLayer.entity.ClienteEntity;
import persistanceLayer.entity.ReservaEntity;
import persistanceLayer.entity.TransporteEntity;
import persistanceLayer.entity.ViajeEntity;

@Mapper(componentModel = "spring")
public interface mapper {

	ViajeDTO viajeToDto(ViajeEntity entity);

	@Mapping(target = "reservas", ignore = true)
	@Mapping(target = "transportes", ignore = true)
	ViajeEntity viajeToEntity(ViajeDTO dto);

	@Mapping(target = "idViaje", source = "viaje.idViaje")
	@Mapping(target = "idCliente", source = "cliente.idCliente")
	ReservaDTO reservaToDto(ReservaEntity entity);

	@Mapping(target = "viaje", ignore = true)
	@Mapping(target = "cliente", ignore = true)
	ReservaEntity reservaToEntity(ReservaDTO dto);

	ClienteDTO clienteToDto(ClienteEntity entity);

	@Mapping(target = "reservas", ignore = true)
	ClienteEntity clienteToEntity(ClienteDTO dto);

	@Mapping(target = "idViaje", source = "viaje.idViaje")
	TransporteDTO transporteToDto(TransporteEntity entity);

	@Mapping(target = "viaje", ignore = true)
	TransporteEntity transporteToEntity(TransporteDTO dto);
}
