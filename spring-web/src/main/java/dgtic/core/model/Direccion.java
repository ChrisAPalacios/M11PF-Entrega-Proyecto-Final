package dgtic.core.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "DIRECCION")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Direccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DIRECCION")
    private Integer idDireccion;

    @Column(name = "CALLE", nullable = false, length = 150)
    private String calle;

    @Column(name = "NUMINT", length = 10)
    private String numInt;

    @Column(name = "NUMEXT", nullable = false, length = 10)
    private String numExt;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "ID_ALCALDIA", nullable = false)
    private Alcaldia alcaldia;
}

