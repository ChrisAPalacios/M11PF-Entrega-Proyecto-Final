package dgtic.core.service;

import dgtic.core.model.Devolucion;
import dgtic.core.model.Usuario;
import dgtic.core.repository.DevolucionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DevolucionService {

    @Autowired
    private DevolucionRepository devolucionRepository;

    public Devolucion guardar(Devolucion devolucion) {
        return devolucionRepository.save(devolucion);
    }

}
