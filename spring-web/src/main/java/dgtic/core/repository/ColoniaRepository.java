package dgtic.core.repository;

import dgtic.core.model.Colonia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ColoniaRepository extends JpaRepository<Colonia, Integer> {
    List<Colonia> findByAlcaldia_IdAlcaldia(Integer idAlcaldia);
}


