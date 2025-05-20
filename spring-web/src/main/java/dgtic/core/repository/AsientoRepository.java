package dgtic.core.repository;

import dgtic.core.model.Asiento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AsientoRepository extends JpaRepository<Asiento, Integer> {
    List<Asiento> findByZona_IdZona(Integer idZona);
    boolean existsByNumeroAsientoAndZona_IdZona(String numeroAsiento, Integer idZona);
    Page<Asiento> findByZonaIdZona(Integer idZona, Pageable pageable);
    Asiento findByNumeroAsientoAndZona_IdZona(String numeroAsiento, Integer idZona);

}
