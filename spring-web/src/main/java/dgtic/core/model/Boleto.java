package dgtic.core.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "BOLETO")
@Getter
@Setter
public class Boleto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_BOLETO")
    private Integer idBoleto;

    @ManyToOne
    @JoinColumn(name = "ID_COMPRA", nullable = false)
    private Compra compra;

    @Column(name = "ESTADO", nullable = false)
    private String estado;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "ID_DEVOLUCION")
    private Devolucion devolucion;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "ID_ASIENTO", referencedColumnName = "ID_ASIENTO"),
            @JoinColumn(name = "ID_EVENTO", referencedColumnName = "ID_EVENTO")
    })
    private AsientoEvento asientoEvento;

}
