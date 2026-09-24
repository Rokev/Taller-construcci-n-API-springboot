import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(descripcion = "informacion del viaje")
public class ViajeDTO{

    private Long idViaje;

    private String destino;

    private int duracionDias;

    private double precio;

    private DateTime fechasDisponibles;

    private String descripcion;

}