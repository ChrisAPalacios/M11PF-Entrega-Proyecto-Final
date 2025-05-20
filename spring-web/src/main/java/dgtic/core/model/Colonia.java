package dgtic.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "COLONIA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Colonia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_COLONIA")
    private Integer idColonia;

    @Column(name = "NOMBRE_COLONIA", nullable = false, unique = true, length = 50)
    private String nombreColonia;

    @ManyToOne
    @JoinColumn(name = "ID_ALCALDIA", nullable = false)
    @JsonIgnoreProperties("colonias")
    private Alcaldia alcaldia;
}

