package persistanceLayer.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "cliente")
@Data
@AllArgsConstructor
public class ClienteEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_cliente")
    private Long idCliente;

    private String nombre;
    
    private String email;
    
    private String direccion;

    @OneToMany(mappedBy = "ClienteEntity", fetch = FetchType.LAZY)
    private List<ReservaEntity> reservas;
}