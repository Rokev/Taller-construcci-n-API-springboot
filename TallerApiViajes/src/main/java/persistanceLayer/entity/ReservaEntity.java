package persistanceLayer.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import jakarta.persistence.*;

import java.Time.LocalDateTime;

@Entity
@Table(name = "reserva")
@Data
@AllArgsConstructor
public class ReservaEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_reserva")
    private Long idReserva;

    private LocalDateTime fecha;
    
    private String estado;
    
    @Column(name="numero_personas")
    private int numeroPersonas;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_viaje")
    private ViajeEntity viaje;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente")
    private ClienteEntity cliente;
}