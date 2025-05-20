package dgtic.core.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
public class AsientoEventoId implements Serializable {

    @Column(name = "ID_EVENTO")
    private Integer idEvento;

    @Column(name = "ID_ASIENTO")
    private Integer idAsiento;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AsientoEventoId that)) return false;
        return Objects.equals(idAsiento, that.idAsiento) &&
                Objects.equals(idEvento, that.idEvento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAsiento, idEvento);
    }
    public AsientoEventoId() {
    }

    public AsientoEventoId(Integer idEvento, Integer idAsiento) {
        this.idEvento = idEvento;
        this.idAsiento = idAsiento;
    }
}
