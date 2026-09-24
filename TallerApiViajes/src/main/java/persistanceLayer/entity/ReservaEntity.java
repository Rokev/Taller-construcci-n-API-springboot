import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.LocalTime;

@Entity
@Table(name = "reserva")
@Data
@AllArgsConstructor
public class ReservaEntity{
    @id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @column(name="id_reserva")
    private Long idReserva;
    private LocalTime fecha;
    private String estado;
    @column(name="numero_personas")
    private int numeroPersonas;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_viaje")
    private ViajeEntity viaje;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente")
    private ClienteEntity cliente;
}