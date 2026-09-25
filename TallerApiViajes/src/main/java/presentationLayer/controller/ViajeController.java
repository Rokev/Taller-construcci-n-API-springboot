package presentationLayer.controller;

import BussisnesCatLayer.dto.ViajeDTO;
import BussisnesCatLayer.service.ViajeService;
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
@RequestMapping("/api/v1/viajes")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Viajes", description = "Operaciones CRUD para la gestion de viajes")
public class ViajeController {

    private final ViajeService viajeService;

    @PostMapping
    @Operation(summary = "Crear un nuevo viaje", description = "Registra un nuevo viaje en el sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Viaje creado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ViajeDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos invalidos o faltantes",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<ViajeDTO> crearViaje(
            @Parameter(description = "Datos del viaje a crear", required = true)
            @Valid @RequestBody ViajeDTO viajeDTO) {
        log.info("POST /api/v1/viajes - destino: {}", viajeDTO.getDestino());
        ViajeDTO creado = viajeService.crearViaje(viajeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar viaje por ID", description = "Obtiene la informacion de un viaje especifico")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Viaje encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ViajeDTO.class))),
            @ApiResponse(responseCode = "404", description = "Viaje no encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<ViajeDTO> obtenerPorId(
            @Parameter(description = "ID del viaje", required = true, example = "1")
            @PathVariable Long id) {
        log.debug("GET /api/v1/viajes/{}", id);
        return ResponseEntity.ok(viajeService.obtenerViajePorId(id));
    }

    @GetMapping
    @Operation(summary = "Listar todos los viajes", description = "Obtiene la lista completa de viajes registrados")
    @ApiResponse(responseCode = "200", description = "Lista de viajes obtenida exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ViajeDTO.class)))
    public ResponseEntity<List<ViajeDTO>> obtenerTodos() {
        log.debug("GET /api/v1/viajes");
        return ResponseEntity.ok(viajeService.obtenerTodosLosViajes());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar viaje", description = "Actualiza los datos de un viaje existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Viaje actualizado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ViajeDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos invalidos",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Viaje no encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<ViajeDTO> actualizar(
            @Parameter(description = "ID del viaje a actualizar", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "Nuevos datos del viaje", required = true)
            @Valid @RequestBody ViajeDTO viajeDTO) {
        log.info("PUT /api/v1/viajes/{}", id);
        return ResponseEntity.ok(viajeService.actualizarViaje(id, viajeDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar viaje", description = "Elimina un viaje del sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Viaje eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Viaje no encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del viaje a eliminar", required = true, example = "1")
            @PathVariable Long id) {
        log.info("DELETE /api/v1/viajes/{}", id);
        viajeService.eliminarViaje(id);
        return ResponseEntity.noContent().build();
    }
}