package dgtic.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "ZONA")
@Getter
@Setter
public class Zona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ZONA")
    private Integer idZona;

    @Column(name = "NOMBREZONA", nullable = false)
    private String nombreZona;

    @Column(name = "PRECIO", nullable = false)
    private Double precio;

    @Column(name = "CAPACIDAD", nullable = false)
    private Integer capacidad;

    public Zona() {
    }

    public Zona(String nombreZona, Double precio, Integer capacidad) {
        this.nombreZona = nombreZona;
        this.precio = precio;
        this.capacidad = capacidad;
    }

    @OneToMany(mappedBy = "zona")
    @JsonIgnore
    private List<Asiento> asientos;


}
