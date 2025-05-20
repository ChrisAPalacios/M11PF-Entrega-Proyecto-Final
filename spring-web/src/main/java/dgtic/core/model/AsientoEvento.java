package dgtic.core.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ASIENTO_EVENTO")
@Getter
@Setter
public class AsientoEvento {

    @EmbeddedId
    private AsientoEventoId id;

    @NotNull
    @Column(name = "ESTADO", length = 20, nullable = false)
    private String estado = "DISPONIBLE";

    @ManyToOne
    @MapsId("idEvento")
    @JoinColumn(name = "ID_EVENTO", referencedColumnName = "ID_EVENTO")
    private Evento evento;

    @ManyToOne
    @MapsId("idAsiento")
    @JoinColumn(name = "ID_ASIENTO", referencedColumnName = "ID_ASIENTO")
    private Asiento asiento;

    public AsientoEvento() {
    }

    public AsientoEvento(AsientoEventoId id, Evento evento, Asiento asiento, String estado) {
        this.id = id;
        this.evento = evento;
        this.asiento = asiento;
        this.estado = estado;
    }
}
