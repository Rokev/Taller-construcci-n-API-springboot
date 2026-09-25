import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Schema(description = "informacion del viaje")
public class ViajeDTO{
    @Schema(description = "id unico de viaje", example = "444", accessMode = Schema.AccessMode.READ_ONLY)
    private Long idViaje;
    @Schema(description = "lugar de destino", example = "marruecos", required = true)
    private String destino;
    @Schema(description = "numero de dias que durara el viaje ", example = "4", required = true)
    private int duracionDias;
    @Schema(description = "valor de el viaje", example = "2000000", required = true)
    private double precio;
    @Schema(description = "Nombre del cliente", example = "2025-09-07T10:30:00", required = true)
    private LocalDateTime fechasDisponibles;
    @Schema(description = "descripcion detallada de lugares incluidos", example = "incluye visita a las islas de san andres con hospedaje tour por las playas y montar un camello", required = true)
    private String descripcion;

}