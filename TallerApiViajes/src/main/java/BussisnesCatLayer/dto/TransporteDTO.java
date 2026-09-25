import BussisnesCatLayer.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Schema(descripcion = "informacion del transporte")
public class TransporteDTO {
    @Schema(description = "ID único del transporte", example = "123", accessMode = Schema.AccessMode.READ_ONLY)
    private Long idTransporte;
    @Schema(description = "Nombre de la compañia del transporte", example = "Coochoferes", required = true)
    private String compania;
    @Schema(description = "Horario de salida del transporte", example = "2026-10-07T11:30:00", required = true)
    private LocalDateTime horario;
    @Schema(description = "Duracion del viaje en horas", example = "10", required = true)
    private int duracion;
    @Schema(description = "Tipo de clase que se toma el servicio como primera, clase ejecutiva, etc", example = "primera clase", required = true)
    private String claseServicio;
    @Schema(description = "ID del viaje asignado", example = "4", required = true)
    private Long idViaje;

}