package presentationLayer.controller;

import BussisnesCatLayer.dto.ReservaDTO;
import BussisnesCatLayer.service.ReservaService;
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
@RequestMapping("/api/v1/reservas")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Reservas", description = "Operaciones CRUD para la gestion de reservas de clientes sobre viajes")
public class ReservaController {

    private final ReservaService reservaService;

    @PostMapping
    @Operation(
            summary = "Crear una nueva reserva",
            description = "Registra una reserva. Valida que el viaje y el cliente existan y que la fecha y el estado sean validos"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Reserva creada exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ReservaDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos invalidos, viaje/cliente inexistente, fecha o estado invalido",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<ReservaDTO> crearReserva(
            @Parameter(description = "Datos de la reserva a crear", required = true)
            @Valid @RequestBody ReservaDTO reservaDTO) {
        log.info("POST /api/v1/reservas - viaje ID: {}, cliente ID: {}",
                reservaDTO.getIdViaje(), reservaDTO.getIdCliente());
        ReservaDTO creada = reservaService.crearReserva(reservaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar reserva por ID", description = "Obtiene la informacion de una reserva especifica")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reserva encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ReservaDTO.class))),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<ReservaDTO> obtenerPorId(
            @Parameter(description = "ID de la reserva", required = true, example = "1")
            @PathVariable Long id) {
        log.debug("GET /api/v1/reservas/{}", id);
        return ResponseEntity.ok(reservaService.obtenerReservaPorId(id));
    }

    @GetMapping
    @Operation(summary = "Listar todas las reservas", description = "Obtiene la lista completa de reservas registradas")
    @ApiResponse(responseCode = "200", description = "Lista de reservas obtenida exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ReservaDTO.class)))
    public ResponseEntity<List<ReservaDTO>> obtenerTodas() {
        log.debug("GET /api/v1/reservas");
        return ResponseEntity.ok(reservaService.obtenerTodasLasReservas());
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar reserva",
            description = "Actualiza los datos de una reserva existente, validando nuevamente viaje, cliente, fecha y estado"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reserva actualizada exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ReservaDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos invalidos, viaje/cliente inexistente, fecha o estado invalido",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<ReservaDTO> actualizar(
            @Parameter(description = "ID de la reserva a actualizar", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "Nuevos datos de la reserva", required = true)
            @Valid @RequestBody ReservaDTO reservaDTO) {
        log.info("PUT /api/v1/reservas/{}", id);
        return ResponseEntity.ok(reservaService.actualizarReserva(id, reservaDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar reserva", description = "Elimina una reserva del sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Reserva eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID de la reserva a eliminar", required = true, example = "1")
            @PathVariable Long id) {
        log.info("DELETE /api/v1/reservas/{}", id);
        reservaService.eliminarReserva(id);
        return ResponseEntity.noContent().build();
    }
}