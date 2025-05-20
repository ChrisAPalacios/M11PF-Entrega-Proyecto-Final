package dgtic.core.repository;

import dgtic.core.model.AsientoEvento;
import dgtic.core.model.AsientoEventoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AsientoEventoRepository extends JpaRepository<AsientoEvento, AsientoEventoId> {
    List<AsientoEvento> findByEvento_IdEvento(Integer idEvento);
    List<AsientoEvento> findByEvento_IdEventoAndEstado(Integer idEvento, String estado);
    @Query("SELECT ae FROM AsientoEvento ae WHERE ae.asiento.zona.idZona = :idZona AND ae.evento.idEvento = :idEvento")
    List<AsientoEvento> buscarPorZonaYEvento(@Param("idZona") Integer idZona, @Param("idEvento") Integer idEvento);
    List<AsientoEvento> findByAsiento_Zona_IdZona(Integer idZona);
    @Query("SELECT ae FROM AsientoEvento ae WHERE ae.evento.idEvento = :idEvento AND ae.asiento.zona.idZona = :idZona")
    List<AsientoEvento> findByEventoIdAndZonaId(@Param("idEvento") Integer idEvento,
                                                @Param("idZona") Integer idZona);



}
