package dgtic.core.service;

import dgtic.core.model.Alcaldia;
import dgtic.core.repository.AlcaldiaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlcaldiaService {
    private final AlcaldiaRepository repo;
    public AlcaldiaService(AlcaldiaRepository repo) { this.repo = repo; }
    public List<Alcaldia> listarTodas() { return repo.findAll(); }
    public Optional<Alcaldia> buscarPorId(Integer id) { return repo.findById(id); }
    public Alcaldia guardar(Alcaldia a) { return repo.save(a); }
    public void eliminar(Integer id) { repo.deleteById(id); }
}
