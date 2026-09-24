import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.DateTime;

@Entity
@Table(name = "viaje")
@Data
@AllArgsConstructor
public class ViajeEntity{
    @id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @column(name="id_viaje")
    private Long idViaje;
    private String destino;
    @column(name="duracion_dias")
    private int duracionDias;
    private double precio;
    @column(name="fechas_disponibles")
    private DateTime fechasDisponibles;
    private String descripcion;

    @OneToMany(mappedBy = "ReservaEntity", fetch = FetchType.LAZY)
    private List<ReservaEntity> reservas;

    @OneToMany(mappedBy = "TransporteEntity", fetch = FetchType.LAZY)
    private List<TransporteEntity> transportes;
}