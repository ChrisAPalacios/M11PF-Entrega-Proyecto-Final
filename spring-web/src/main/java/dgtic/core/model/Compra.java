package dgtic.core.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "COMPRA")
@Getter
@Setter
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_COMPRA")
    private Integer idCompra;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO", nullable = false)
    private Usuario usuario;

    @Column(name = "MONTOTOTAL", nullable = false)
    private Double montoTotal;

    @Column(name = "FECHACOMPRA", nullable = false)
    private LocalDateTime fechaCompra;


    @OneToMany(mappedBy = "compra", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Boleto> boletos;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_COMPRA")
    private Devolucion devolucion;
}
