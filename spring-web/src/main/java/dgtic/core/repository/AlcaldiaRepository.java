package dgtic.core.repository;

import dgtic.core.model.Alcaldia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlcaldiaRepository extends JpaRepository<Alcaldia, Integer> {
    Alcaldia findByNombreAlcaldia(String nombre);
}
