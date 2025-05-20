package dgtic.core.service;

import dgtic.core.model.Direccion;
import dgtic.core.repository.DireccionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DireccionService {
    private final DireccionRepository repo;
    public DireccionService(DireccionRepository repo) { this.repo = repo; }
    public Optional<Direccion> buscarPorId(Integer id) { return repo.findById(id); }
    public Direccion guardar(Direccion d) { return repo.save(d); }
    public void eliminar(Integer id) { repo.deleteById(id); }
}
