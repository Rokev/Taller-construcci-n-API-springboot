import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(descripcion = "informacion del cliente")
public class ClienteDTO {

    private Long idCliente;

    private String nombre;

    private String email;

    private String direccion;

    private List<ReservaEntity> reservas;
}