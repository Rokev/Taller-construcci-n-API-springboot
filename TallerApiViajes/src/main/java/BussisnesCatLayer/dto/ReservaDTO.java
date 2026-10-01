package BussisnesCatLayer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "informacion de la reserva")
public class ReservaDTO{
    @Schema(description = "ID único de la reserva", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long idReserva;

    @NotNull(message = "La fecha de la reserva es obligatoria")
    @Schema(description = "fecha de la reserva", example = "2026-10-30T15:45:00", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime fecha;

    @NotBlank(message = "El estado de la reserva es obligatorio")
    @Schema(description = "Estado de la reserva (activa, cancelada, pendiente)", example = "activa", requiredMode = Schema.RequiredMode.REQUIRED)
    private String estado;

    @Positive(message = "El numero de personas debe ser mayor a 0")
    @Schema(description = "numero de personas que se registran a la reserva", example = "4", requiredMode = Schema.RequiredMode.REQUIRED)
    private int numeroPersonas;

    @NotNull(message = "El ID del viaje es obligatorio")
    @Schema(description = "ID del viaje asignado", example = "4", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long idViaje;

    @NotNull(message = "El ID del cliente es obligatorio")
    @Schema(description = "ID del cliente asignado a la reserva", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long idCliente;
}
