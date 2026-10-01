package persistanceLayer.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "viaje")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViajeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_viaje")
    private Long idViaje;
    private String destino;
    @Column(name="duracion_dias")
    private int duracionDias;
    private double precio;
    @Column(name="fechas_disponibles")
    private LocalDateTime fechasDisponibles;
    private String descripcion;

    @OneToMany(mappedBy = "viaje", fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<ReservaEntity> reservas;

    @OneToMany(mappedBy = "viaje", fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<TransporteEntity> transportes;
}
