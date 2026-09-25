package persistanceLayer.mapper;
import BussisnesCatLayer.dto.ClienteDTO;
import BussisnesCatLayer.dto.ReservaDTO;
import BussisnesCatLayer.dto.TransporteDTO;
import BussisnesCatLayer.dto.ViajeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import persistanceLayer.entity.ClienteEntity;
import persistanceLayer.entity.ReservaEntity;
import persistanceLayer.entity.Transporte;
import persistanceLayer.entity.ViajeEntity;

@Mapper(componentModel = "spring")
public interface mapper {

	ViajeDTO toDto(ViajeEntity entity);

	@Mapping(target = "fechasDisponibles", ignore = true)
	@Mapping(target = "reservas", ignore = true)
	@Mapping(target = "transportes", ignore = true)
	ViajeEntity toEntity(ViajeDTO dto);

	@Mapping(target = "idViaje", source = "viaje.idViaje")
	@Mapping(target = "idCliente", source = "cliente.idCliente")
	ReservaDTO toDto(ReservaEntity entity);

	@Mapping(target = "fecha", ignore = true)
	@Mapping(target = "viaje", ignore = true)
	@Mapping(target = "cliente", ignore = true)
	ReservaEntity toEntity(ReservaDTO dto);

	ClienteDTO toDto(ClienteEntity entity);

	@Mapping(target = "reservas", ignore = true)
	ClienteEntity toEntity(ClienteDTO dto);

	@Mapping(target = "idViaje", source = "viaje.idViaje")
	TransporteDTO toDto(TransporteEntity entity);

	@Mapping(target = "viaje", ignore = true)
	TransporteEntity toEntity(TransporteDTO dto);
}
