package persistanceLayer.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "transporte")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransporteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transporte")
    private Long idTransporte;

    private String compania;

    private LocalDateTime horario;

    private int duracion;

    @Column(name = "clase_servicio")
    private String claseServicio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_viaje")
    private ViajeEntity viaje;

}
