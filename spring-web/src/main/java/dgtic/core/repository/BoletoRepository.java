package dgtic.core.repository;

import dgtic.core.model.Boleto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoletoRepository extends JpaRepository<Boleto, Integer> {

    @Query("""
    SELECT b FROM Boleto b
    WHERE b.asientoEvento.evento.idEvento = :idEvento
""")
    List<Boleto> findByEvento_IdEvento(@Param("idEvento") Integer idEvento);
    List<Boleto> findByCompra_IdCompra(Integer idCompra);
    Page<Boleto> findByUsuario_IdUsuarioAndEstadoNotIn(Integer idUsuario, List<String> estados, Pageable pageable);
    List<Boleto> findByUsuario_IdUsuario(Integer idUsuario);
}
