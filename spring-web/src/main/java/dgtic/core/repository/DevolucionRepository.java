package dgtic.core.repository;

import dgtic.core.model.Devolucion;
import dgtic.core.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DevolucionRepository extends JpaRepository<Devolucion, Integer> {
    List<Devolucion> findByCompra_Usuario(Usuario usuario);
}
