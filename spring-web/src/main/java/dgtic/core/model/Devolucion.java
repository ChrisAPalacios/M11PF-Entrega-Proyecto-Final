package dgtic.core.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "DEVOLUCION")
@Getter
@Setter
public class Devolucion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DEVOLUCION")
    private Integer idDevolucion;

    @Column(name = "FECHA_DEVOLUCION")
    private LocalDateTime fechaDevolucion;

    @Column(name = "MOTIVO", columnDefinition = "TEXT")
    private String motivo;

    @OneToOne
    @JoinColumn(name = "ID_COMPRA", nullable = false, unique = true)
    private Compra compra;
}
