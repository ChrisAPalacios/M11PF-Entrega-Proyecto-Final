package dgtic.core.service;

import dgtic.core.model.AsientoEvento;
import dgtic.core.model.AsientoEventoId;
import dgtic.core.repository.AsientoEventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AsientoEventoService {

    @Autowired
    private AsientoEventoRepository asientoEventoRepository;

    public List<AsientoEvento> obtenerTodos() {
        return asientoEventoRepository.findAll();
    }

    public List<AsientoEvento> obtenerDisponiblesPorEvento(Integer idEvento) {
        return asientoEventoRepository.findByEvento_IdEventoAndEstado(idEvento, "DISPONIBLE");

    }

    public Optional<AsientoEvento> buscarPorId(AsientoEventoId id) {
        return asientoEventoRepository.findById(id);
    }

    public AsientoEvento guardar(AsientoEvento asientoEvento) {
        return asientoEventoRepository.save(asientoEvento);
    }

    public boolean existePorId(AsientoEventoId id) {
        return asientoEventoRepository.existsById(id);
    }

    public List<AsientoEvento> buscarPorZona(Integer idZona) {
        return asientoEventoRepository.findByAsiento_Zona_IdZona(idZona);
    }

    public List<AsientoEvento> buscarDisponiblesPorZonaYEvento(Integer idZona, Integer idEvento) {
        return asientoEventoRepository.buscarPorZonaYEvento(idZona, idEvento).stream()
                .filter(ae -> "DISPONIBLE".equals(ae.getEstado()))
                .toList();
    }

    public List<AsientoEvento> obtenerPorEventoYZona(Integer idEvento, Integer idZona) {
        return asientoEventoRepository.findByEventoIdAndZonaId(idEvento, idZona);
    }

    public AsientoEvento buscarPorIdSimple(AsientoEventoId id) {
        return asientoEventoRepository.findById(id).orElse(null);
    }

}
