package dgtic.core.repository;

import dgtic.core.model.Evento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Integer> {
    List<Evento> findByTipoEvento_IdTipoEvento(Integer idTipoEvento);
    List<Evento> findTop5ByOrderByFechaDesc();
    List<Evento> findByNombreEventoContainingIgnoreCase(String nombreEvento);
    Page<Evento> findByTipoEvento_IdTipoEvento(Integer idTipoEvento, Pageable pageable);

}
