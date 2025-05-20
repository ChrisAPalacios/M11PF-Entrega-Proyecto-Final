package dgtic.core.repository;

import dgtic.core.model.Compra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Integer> {
    @Query("SELECT c FROM Compra c LEFT JOIN FETCH c.boletos WHERE c.idCompra = :id")
    Compra findByIdWithBoletos(@Param("id") Integer id);

}
