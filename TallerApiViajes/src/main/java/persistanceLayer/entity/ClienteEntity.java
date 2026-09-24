import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.DateTime;

@Entity
@Table(name = "cliente")
@Data
@AllArgsConstructor
public class ClienteEntity{
    @id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @column(name="id_cliente")
    private Long idCliente;
    private String nombre;
    private String email;
    private String direccion;

    @OneToMany(mappedBy = "ReservaEntity", fetch = FetchType.LAZY)
    private List<ReservaEntity> reservas;
}