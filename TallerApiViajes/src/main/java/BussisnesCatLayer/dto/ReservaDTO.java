import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.LocalDateTime

@Data
@AllArgsConstructor
@Schema(descripcion = "informacion de la reserva")
public class ReservaDTO{
    @Schema(description = "ID único de la reserva", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long idReserva;
    @Schema(description = "fecha de la reserva", example = "2026-09-30T15:45:0", required = true)
    private LocalDateTime fecha;
    @Schema(description = "Estado de la reserva (activa, cancelada, pendiente)", example = "activa", required = true)
    private String estado;
    @Schema(description = "numero de personas que se registran a la reserva", example = "4", required = true)
    private int numeroPersonas;
    @Schema(description = "ID del viaje asignado", example = "4", required = true)
    private Long idViaje;
    @Schema(description = "ID del cliente asignado a la reserva", example = "2", required = true)
    private Long idCliente;
}