package dgtic.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Entity
@Table(name = "EVENTO")
@Setter
@Getter
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_EVENTO")
    private Integer idEvento;

    @Column(name = "NOMBRE_EVENTO", nullable = false, length = 150)
    private String nombreEvento;

    @Column(name = "FECHA")
    @Temporal(TemporalType.DATE)
    private Date fecha;

    @Column(name = "DURACION")
    private Integer duracion;

    @ManyToOne
    @JoinColumn(name = "ID_TIPOEVENTO", nullable = false)
    @JsonIgnoreProperties(value = {"eventos"}, allowSetters = true)    private TipoEvento tipoEvento;
    @Column(name = "POSTER")
    private String poster;



    public Evento() {
    }

    public Evento(String nombreEvento, Date fecha, Integer duracion, TipoEvento tipoEvento) {
        this.nombreEvento = nombreEvento;
        this.fecha = fecha;
        this.duracion = duracion;
        this.tipoEvento = tipoEvento;
    }
}