package dgtic.core.service;

import dgtic.core.model.*;
import dgtic.core.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;
    @Autowired
    private ZonaService zonaService;

    @Autowired
    private AsientoService asientoService;

    @Autowired
    private AsientoEventoService asientoEventoService;


    public List<Evento> obtenerTodos() {
        return eventoRepository.findAll();
    }

    public Evento buscarPorId(Integer id) {
        Optional<Evento> optional = eventoRepository.findById(id);
        return optional.orElse(null);
    }

    public Evento guardar(Evento evento) {
        Evento eventoGuardado = eventoRepository.save(evento);
        generarAsientosParaNuevoEvento(eventoGuardado);
        return eventoGuardado;
    }

    private void generarAsientosParaNuevoEvento(Evento evento) {
        List<Zona> zonas = zonaService.obtenerTodas();

        for (Zona zona : zonas) {
            List<Asiento> asientos = asientoService.obtenerPorZona(zona.getIdZona());

            for (Asiento asiento : asientos) {
                AsientoEventoId id = new AsientoEventoId(evento.getIdEvento(), asiento.getIdAsiento());

                // Evita duplicados si por alguna razón ya existen
                if (asientoEventoService.existePorId(id)) continue;

                AsientoEvento nuevo = new AsientoEvento();
                nuevo.setId(id);
                nuevo.setAsiento(asiento);
                nuevo.setEvento(evento);
                nuevo.setEstado("DISPONIBLE");

                asientoEventoService.guardar(nuevo);
            }
        }
    }

    public List<Evento> obtenerEventosAleatorios(int cantidad) {
        List<Evento> eventos = eventoRepository.findAll();
        Collections.shuffle(eventos);
        return eventos.stream().limit(cantidad).toList();
    }
    public void eliminar(Integer id) {
        eventoRepository.deleteById(id);
    }


    public List<Evento> buscarPorNombre(String nombre) {
        return eventoRepository.findByNombreEventoContainingIgnoreCase(nombre);
    }
    public Page<Evento> obtenerTodosPaginado(Pageable pageable) {
        return eventoRepository.findAll(pageable);
    }

    public Page<Evento> obtenerPorCategoriaPaginado(Integer categoriaId, Pageable pageable) {
        return eventoRepository.findByTipoEvento_IdTipoEvento(categoriaId, pageable);
    }


    public Evento findById(Integer idEvento) {
        return eventoRepository.findById(idEvento).orElse(null);
    }
}
