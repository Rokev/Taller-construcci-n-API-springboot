package presentationLayer.controller;

import BussisnesCatLayer.dto.TransporteDTO;
import BussisnesCatLayer.service.TransporteService;
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
@RequestMapping("/api/v1/transportes")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Transportes", description = "Operaciones CRUD para la gestion de transportes asociados a un viaje")
public class TransporteController {

    private final TransporteService transporteService;

    @PostMapping
    @Operation(summary = "Crear un nuevo transporte", description = "Registra un nuevo transporte asociado a un viaje existente")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Transporte creado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransporteDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos invalidos o el viaje asociado no existe",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<TransporteDTO> crearTransporte(
            @Parameter(description = "Datos del transporte a crear", required = true)
            @Valid @RequestBody TransporteDTO transporteDTO) {
        log.info("POST /api/v1/transportes - viaje ID: {}", transporteDTO.getIdViaje());
        TransporteDTO creado = transporteService.crearTransporte(transporteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar transporte por ID", description = "Obtiene la informacion de un transporte especifico")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Transporte encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransporteDTO.class))),
            @ApiResponse(responseCode = "404", description = "Transporte no encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<TransporteDTO> obtenerPorId(
            @Parameter(description = "ID del transporte", required = true, example = "1")
            @PathVariable Long id) {
        log.debug("GET /api/v1/transportes/{}", id);
        return ResponseEntity.ok(transporteService.obtenerTransportePorId(id));
    }

    @GetMapping
    @Operation(summary = "Listar todos los transportes", description = "Obtiene la lista completa de transportes registrados")
    @ApiResponse(responseCode = "200", description = "Lista de transportes obtenida exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransporteDTO.class)))
    public ResponseEntity<List<TransporteDTO>> obtenerTodos() {
        log.debug("GET /api/v1/transportes");
        return ResponseEntity.ok(transporteService.obtenerTodosLosTransportes());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar transporte", description = "Actualiza los datos de un transporte existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Transporte actualizado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransporteDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos invalidos o el viaje asociado no existe",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Transporte no encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<TransporteDTO> actualizar(
            @Parameter(description = "ID del transporte a actualizar", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "Nuevos datos del transporte", required = true)
            @Valid @RequestBody TransporteDTO transporteDTO) {
        log.info("PUT /api/v1/transportes/{}", id);
        return ResponseEntity.ok(transporteService.actualizarTransporte(id, transporteDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar transporte", description = "Elimina un transporte del sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Transporte eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Transporte no encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del transporte a eliminar", required = true, example = "1")
            @PathVariable Long id) {
        log.info("DELETE /api/v1/transportes/{}", id);
        transporteService.eliminarTransporte(id);
        return ResponseEntity.noContent().build();
    }
}
