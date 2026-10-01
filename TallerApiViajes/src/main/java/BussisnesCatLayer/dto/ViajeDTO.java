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
@Schema(description = "informacion del viaje")
public class ViajeDTO{
    @Schema(description = "id unico de viaje", example = "444", accessMode = Schema.AccessMode.READ_ONLY)
    private Long idViaje;

    @NotBlank(message = "El destino es obligatorio")
    @Schema(description = "lugar de destino", example = "marruecos", requiredMode = Schema.RequiredMode.REQUIRED)
    private String destino;

    @Positive(message = "La duracion en dias debe ser mayor a 0")
    @Schema(description = "numero de dias que durara el viaje ", example = "4", requiredMode = Schema.RequiredMode.REQUIRED)
    private int duracionDias;

    @Positive(message = "El precio debe ser mayor a 0")
    @Schema(description = "valor de el viaje", example = "2000000", requiredMode = Schema.RequiredMode.REQUIRED)
    private double precio;

    @NotNull(message = "La fecha disponible es obligatoria")
    @Schema(description = "fecha disponible para realizar el viaje", example = "2026-10-07T10:30:00", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime fechasDisponibles;

    @NotBlank(message = "La descripcion es obligatoria")
    @Schema(description = "descripcion detallada de lugares incluidos", example = "incluye visita a las islas de san andres con hospedaje tour por las playas y montar un camello", requiredMode = Schema.RequiredMode.REQUIRED)
    private String descripcion;

}
