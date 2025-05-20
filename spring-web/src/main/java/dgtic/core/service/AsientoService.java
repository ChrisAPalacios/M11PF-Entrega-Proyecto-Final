package dgtic.core.service;

import dgtic.core.model.Asiento;
import dgtic.core.model.AsientoEventoId;
import dgtic.core.repository.AsientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AsientoService {

    @Autowired
    private AsientoRepository asientoRepository;


    public Page<Asiento> obtenerPorZonaPaginado(Integer idZona, Pageable pageable) {
        return asientoRepository.findByZonaIdZona(idZona, pageable);
    }

    public List<Asiento> obtenerTodos() {
        return asientoRepository.findAll();
    }

    public List<Asiento> obtenerPorZona(Integer idZona) {
        return asientoRepository.findByZona_IdZona(idZona);
    }

    public Asiento buscarPorId(Integer id) {
        return asientoRepository.findById(id).orElse(null);
    }

    public void guardar(Asiento asiento) {
        asientoRepository.save(asiento);
    }

    public void eliminar(Integer id) {
        asientoRepository.deleteById(id);
    }

    public boolean existeAsientoEnZona(String numeroAsiento, Integer idZona) {
        return asientoRepository.existsByNumeroAsientoAndZona_IdZona(numeroAsiento, idZona);
    }





}
