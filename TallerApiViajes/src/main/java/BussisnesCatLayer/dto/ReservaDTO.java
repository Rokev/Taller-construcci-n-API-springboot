import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(descripcion = "informacion de la reserva")
public class ReservaDTO{

    private Long idReserva;

    private DateTime fecha;

    private String estado;

    private int numeroPersonas;

    private ViajeEntity viaje;

    private ClienteEntity cliente;
}