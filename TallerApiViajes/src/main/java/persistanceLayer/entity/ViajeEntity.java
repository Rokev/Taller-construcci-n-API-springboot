package persistanceLayer.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "viaje")
@Data
@AllArgsConstructor
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

    @OneToMany(mappedBy = "ViajeEntity", fetch = FetchType.LAZY)
    private List<ReservaEntity> reservas;

    @OneToMany(mappedBy = "ViajeEntity", fetch = FetchType.LAZY)
    private List<TransporteEntity> transportes;
}        