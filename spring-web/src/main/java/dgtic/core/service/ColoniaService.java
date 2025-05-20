package dgtic.core.service;

import dgtic.core.model.Colonia;
import dgtic.core.repository.ColoniaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ColoniaService {
    @Autowired
    private ColoniaRepository coloniaRepository;

    private final ColoniaRepository repo;
    public ColoniaService(ColoniaRepository repo) { this.repo = repo; }

    public List<Colonia> listarTodas() { return repo.findAll(); }
    public List<Colonia> buscarPorAlcaldia(Integer idAlcaldia) {
        return coloniaRepository.findByAlcaldia_IdAlcaldia(idAlcaldia);
    }
    public Optional<Colonia> buscarPorId(Integer id) { return repo.findById(id); }
    public Colonia guardar(Colonia c) { return repo.save(c); }
    public void eliminar(Integer id) { repo.deleteById(id); }
}
