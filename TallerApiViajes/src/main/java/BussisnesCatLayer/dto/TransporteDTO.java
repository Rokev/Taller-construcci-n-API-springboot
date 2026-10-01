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
@Schema(description = "informacion del transporte")
public class TransporteDTO {
    @Schema(description = "ID único del transporte", example = "123", accessMode = Schema.AccessMode.READ_ONLY)
    private Long idTransporte;

    @NotBlank(message = "La compañia es obligatoria")
    @Schema(description = "Nombre de la compañia del transporte", example = "Coochoferes", requiredMode = Schema.RequiredMode.REQUIRED)
    private String compania;

    @NotNull(message = "El horario es obligatorio")
    @Schema(description = "Horario de salida del transporte", example = "2026-10-07T11:30:00", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime horario;

    @Positive(message = "La duracion debe ser mayor a 0 horas")
    @Schema(description = "Duracion del viaje en horas", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
    private int duracion;

    @NotBlank(message = "La clase de servicio es obligatoria")
    @Schema(description = "Tipo de clase que se toma el servicio como primera, clase ejecutiva, etc", example = "primera clase", requiredMode = Schema.RequiredMode.REQUIRED)
    private String claseServicio;

    @NotNull(message = "El ID del viaje es obligatorio")
    @Schema(description = "ID del viaje asignado", example = "4", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long idViaje;

}
