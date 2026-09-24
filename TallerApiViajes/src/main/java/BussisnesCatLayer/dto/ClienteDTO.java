import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(descripcion = "informacion del cliente")
public class ClienteDTO {
    @Schema(description = "id unico de cliente", example = "154", accessMode = Schema.AccessMode.READ_ONLY)
    private Long idCliente;

    @Schema(description = "Nombre del cliente", example = "maria", required = true, maxLength = 50)
    private String nombre;

    @Schema(description = "email", example = "an12@gmail.com", required = true)
    private String email;

    @Schema(description = "lugar de residencia del cliente", example = "calle 35 casa n34-57", required = true)
    private String direccion;


}