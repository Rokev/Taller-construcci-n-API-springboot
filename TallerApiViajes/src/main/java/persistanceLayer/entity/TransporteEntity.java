package persistanceLayer.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "transporte")
@Data
@AllArgsConstructor
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

    @JoinColumn(name = "id_viaje")
    private ViajeEntity viaje;

}