package dgtic.core.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "CLIENTE")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    @Id
    @Column(name = "ID_USUARIO")
    private Integer idUsuario;

    @OneToOne
    @MapsId
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID_USUARIO")
    private Usuario usuario;

    @Column(name = "FECHA_REGISTRO", nullable = false)
    private LocalDate fechaRegistro;

    @Enumerated(EnumType.STRING)
    @Column(name = "METODO_PAGO", nullable = false, length = 10)
    private MetodoPago metodoPago;

    public enum MetodoPago {
        PAYPAL, CREDITO, DEBITO
    }
}
