package dgtic.core.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "TIPO_EVENTO")
@Setter
@Getter
public class TipoEvento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TIPOEVENTO")
    private Integer idTipoEvento;

    @Column(name = "TIPO", nullable = false, length = 150)
    private String tipo;

    @OneToMany(mappedBy = "tipoEvento", cascade = CascadeType.ALL)
    private List<Evento> eventos;

    public TipoEvento() {
    }

    public TipoEvento(String tipo) {
        this.tipo = tipo;
    }


}
