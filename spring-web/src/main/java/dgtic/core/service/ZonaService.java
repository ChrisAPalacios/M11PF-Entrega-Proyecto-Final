// ZonaService.java
package dgtic.core.service;

import dgtic.core.model.Zona;
import dgtic.core.repository.ZonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Service
public class ZonaService {

    @Autowired
    private ZonaRepository zonaRepository;


    public List<Zona> obtenerTodas() {
        return zonaRepository.findAll();
    }

    public Zona guardar(Zona zona) {
        if (zona.getIdZona() != null) {
            Zona existente = zonaRepository.findById(zona.getIdZona()).orElse(null);
            if (existente != null) {
                zona.setCapacidad(existente.getCapacidad());
            }
        }
        return zonaRepository.save(zona);
    }


    public Zona buscarPorId(Integer id) {
        Optional<Zona> optional = zonaRepository.findById(id);
        return optional.orElse(null);
    }

    public void eliminar(Integer id) {
        zonaRepository.deleteById(id);
    }


}
