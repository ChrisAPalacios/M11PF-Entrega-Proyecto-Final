package dgtic.core.repository;

import dgtic.core.model.Zona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZonaRepository extends JpaRepository<Zona, Integer> {
    @Query("""
    SELECT DISTINCT z FROM Zona z
    JOIN Asiento a ON a.zona.idZona = z.idZona
    JOIN AsientoEvento ae ON ae.asiento.idAsiento = a.idAsiento
    WHERE ae.evento.idEvento = :idEvento
""")
    List<Zona> findZonasByEventoId(@Param("idEvento") Integer idEvento);

}
