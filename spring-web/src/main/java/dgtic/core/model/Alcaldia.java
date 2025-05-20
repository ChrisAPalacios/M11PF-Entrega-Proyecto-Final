package dgtic.core.model;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "ALCALDIA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Alcaldia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ALCALDIA")
    private Integer idAlcaldia;

    @Column(name = "NOMBRE_ALCALDIA", nullable = false, unique = true, length = 40)
    private String nombreAlcaldia;

    @OneToMany(mappedBy = "alcaldia", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Direccion> direcciones;

    @OneToMany(mappedBy = "alcaldia", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Colonia> colonias;

}

