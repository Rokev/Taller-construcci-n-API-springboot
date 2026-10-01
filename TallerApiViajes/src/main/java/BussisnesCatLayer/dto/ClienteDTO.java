package BussisnesCatLayer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "informacion del cliente")
public class ClienteDTO {
    @Schema(description = "id unico de cliente", example = "154", accessMode = Schema.AccessMode.READ_ONLY)
    private Long idCliente;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50, message = "El nombre no puede superar los 50 caracteres")
    @Schema(description = "Nombre del cliente", example = "maria", requiredMode = Schema.RequiredMode.REQUIRED, maxLength = 50)
    private String nombre;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email no tiene un formato valido")
    @Schema(description = "email", example = "an12@gmail.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @NotBlank(message = "La direccion es obligatoria")
    @Schema(description = "lugar de residencia del cliente", example = "calle 35 casa n34-57", requiredMode = Schema.RequiredMode.REQUIRED)
    private String direccion;

}
