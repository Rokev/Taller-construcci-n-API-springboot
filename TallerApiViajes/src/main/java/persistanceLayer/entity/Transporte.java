import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.LocalDateTime;

@Entity
@Table(name = "transporte")
@Data
@AllArgsConstructor
public class TransporteEntity {
    @id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @column(name = "id_transporte")
    private Long idTransporte;
    private String compania;
    private LocalDateTime horario;
    private int duracion;
    @column(name = "clase_servicio")
    private String claseServicio;

    @JoinColumn(name = "id_viaje")
    private ViajeEntity viaje;

}