package presentationLayer.controller;

import BussisnesCatLayer.dto.ClienteDTO;
import BussisnesCatLayer.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import presentationLayer.advice.ApiError;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clientes")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Clientes", description = "Operaciones CRUD para la gestion de clientes")
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping
    @Operation(summary = "Crear un nuevo cliente", description = "Registra un nuevo cliente en el sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cliente creado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos invalidos, faltantes o email ya registrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<ClienteDTO> crearCliente(
            @Parameter(description = "Datos del cliente a crear", required = true)
            @Valid @RequestBody ClienteDTO clienteDTO) {
        log.info("POST /api/v1/clientes - email: {}", clienteDTO.getEmail());
        ClienteDTO creado = clienteService.crearCliente(clienteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar cliente por ID", description = "Obtiene la informacion de un cliente especifico")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteDTO.class))),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<ClienteDTO> obtenerPorId(
            @Parameter(description = "ID del cliente", required = true, example = "1")
            @PathVariable Long id) {
        log.debug("GET /api/v1/clientes/{}", id);
        return ResponseEntity.ok(clienteService.obtenerClientePorId(id));
    }

    @GetMapping
    @Operation(summary = "Listar todos los clientes", description = "Obtiene la lista completa de clientes registrados")
    @ApiResponse(responseCode = "200", description = "Lista de clientes obtenida exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteDTO.class)))
    public ResponseEntity<List<ClienteDTO>> obtenerTodos() {
        log.debug("GET /api/v1/clientes");
        return ResponseEntity.ok(clienteService.obtenerTodosLosClientes());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar cliente", description = "Actualiza los datos de un cliente existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente actualizado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos invalidos o email ya registrado por otro cliente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<ClienteDTO> actualizar(
            @Parameter(description = "ID del cliente a actualizar", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "Nuevos datos del cliente", required = true)
            @Valid @RequestBody ClienteDTO clienteDTO) {
        log.info("PUT /api/v1/clientes/{}", id);
        return ResponseEntity.ok(clienteService.actualizarCliente(id, clienteDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar cliente", description = "Elimina un cliente del sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Cliente eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del cliente a eliminar", required = true, example = "1")
            @PathVariable Long id) {
        log.info("DELETE /api/v1/clientes/{}", id);
        clienteService.eliminarCliente(id);
        return ResponseEntity.noContent().build();
    }
}