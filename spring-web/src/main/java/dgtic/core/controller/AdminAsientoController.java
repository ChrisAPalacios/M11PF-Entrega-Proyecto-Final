package dgtic.core.controller;

import dgtic.core.model.Asiento;
import dgtic.core.model.AsientoEvento;
import dgtic.core.model.AsientoEventoId;
import dgtic.core.model.Zona;
import dgtic.core.model.Evento;
import dgtic.core.service.AsientoEventoService;
import dgtic.core.service.AsientoService;
import dgtic.core.service.ZonaService;
import dgtic.core.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/asientos")
public class AdminAsientoController {

    @Autowired
    private AsientoService asientoService;

    @Autowired
    private ZonaService zonaService;

    @Autowired
    private EventoService eventoService;

    @Autowired
    private AsientoEventoService asientoEventoService;

    @GetMapping("/{idZona}/{idEvento}")
    public String listarAsientos(
            @PathVariable("idZona") Integer idZona,
            @PathVariable("idEvento") Integer idEvento,
            @RequestParam(defaultValue = "0") int page,
            Model model) {

        Zona zona = zonaService.buscarPorId(idZona);
        Evento evento = eventoService.buscarPorId(idEvento);

        Pageable pageable = PageRequest.of(page, 5);
        Page<Asiento> asientosPage = asientoService.obtenerPorZonaPaginado(idZona, pageable);

        model.addAttribute("titulo", "Asientos de la Zona: " + zona.getNombreZona());
        model.addAttribute("zona", zona);
        model.addAttribute("evento", evento);
        model.addAttribute("asientosPage", asientosPage);

        return "admin/asientos_por_zona";
    }



    @GetMapping("/nuevo/{idZona}")
    public String nuevoAsiento(@PathVariable("idZona") Integer idZona, Model model) {
        Zona zona = zonaService.buscarPorId(idZona);
        if (zona == null) {
            return "redirect:/admin/zonas";
        }
        Asiento asiento = new Asiento();
        asiento.setZona(zona);
        model.addAttribute("titulo", "Nuevo Asiento en " + zona.getNombreZona());
        model.addAttribute("asiento", asiento);
        return "admin/form_asiento";
    }

    @PostMapping("/guardar")
    public String guardarAsiento(@ModelAttribute Asiento asiento, Model model) {
        Integer idZona = asiento.getZona().getIdZona();

        boolean yaExiste = asiento.getIdAsiento() == null
                || !asiento.getNumeroAsiento().equals(asientoService.buscarPorId(asiento.getIdAsiento()).getNumeroAsiento());

        if (yaExiste && asientoService.existeAsientoEnZona(asiento.getNumeroAsiento(), idZona)) {
            model.addAttribute("titulo", asiento.getIdAsiento() != null ? "Editar Asiento" : "Nuevo Asiento");
            model.addAttribute("asiento", asiento);
            model.addAttribute("error", "Ya existe un asiento con ese número en esta zona.");
            return "admin/form_asiento";
        }

        asientoService.guardar(asiento);
        return "redirect:/admin/asientos/" + idZona;
    }

    @GetMapping("/editar/{id}")
    public String editarAsiento(@PathVariable("id") Integer id, Model model) {
        Asiento asiento = asientoService.buscarPorId(id);
        if (asiento == null) {
            return "redirect:/admin/zonas";
        }
        model.addAttribute("titulo", "Editar Asiento");
        model.addAttribute("asiento", asiento);
        return "admin/form_asiento";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarAsiento(@PathVariable("id") Integer id) {
        Asiento asiento = asientoService.buscarPorId(id);
        if (asiento != null) {
            Integer idZona = asiento.getZona().getIdZona();
            asientoService.eliminar(id);
            return "redirect:/admin/asientos/" + idZona;
        }
        return "redirect:/admin/zonas";
    }

    @GetMapping("/generar-todos-eventos/{idZona}")
    public String generarAsientosParaTodosLosEventos(@PathVariable("idZona") Integer idZona, RedirectAttributes redirectAttributes) {
        Zona zona = zonaService.buscarPorId(idZona);
        if (zona == null) {
            redirectAttributes.addFlashAttribute("error", "Zona no encontrada.");
            return "redirect:/admin/zonas";
        }

        List<Evento> eventos = eventoService.obtenerTodos();
        List<Asiento> asientos = asientoService.obtenerPorZona(idZona);

        for (Evento evento : eventos) {
            for (Asiento asiento : asientos) {
                AsientoEventoId id = new AsientoEventoId();
                id.setIdAsiento(asiento.getIdAsiento());
                id.setIdEvento(evento.getIdEvento());

                if (!asientoEventoService.existePorId(id)) {
                    AsientoEvento relacion = new AsientoEvento();
                    relacion.setId(id);
                    relacion.setEvento(evento);
                    relacion.setAsiento(asiento);
                    relacion.setEstado("DISPONIBLE");

                    asientoEventoService.guardar(relacion);
                }
            }
        }

        redirectAttributes.addFlashAttribute("mensaje", "¡Asientos generados exitosamente para todos los eventos!");
        return "redirect:/admin/zonas";
    }

    @GetMapping("/generar/{idZona}")
    public String generarAsientosPorZona(@PathVariable("idZona") Integer idZona, RedirectAttributes redirectAttributes) {
        Zona zona = zonaService.buscarPorId(idZona);
        if (zona == null) {
            redirectAttributes.addFlashAttribute("error", "Zona no encontrada.");
            return "redirect:/admin/zonas";
        }

        // Validar si ya existen asientos
        if (!asientoService.obtenerPorZona(idZona).isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Los asientos ya fueron generados para esta zona.");
            return "redirect:/admin/asientos/" + idZona + "/1";
        }

        int capacidad = zona.getCapacidad();
        char letraZona = (char) ('A' + (zona.getIdZona() - 1));

        for (int i = 1; i <= capacidad; i++) {
            String numeroAsiento = String.format("%s-%03d", letraZona, i);
            Asiento asiento = new Asiento();
            asiento.setNumeroAsiento(numeroAsiento);
            asiento.setZona(zona);

            asientoService.guardar(asiento);
        }

        redirectAttributes.addFlashAttribute("mensaje", "Asientos generados exitosamente.");
        return "redirect:/admin/asientos/" + idZona + "/1";
    }






}