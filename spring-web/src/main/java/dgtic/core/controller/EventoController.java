package dgtic.core.controller;

import dgtic.core.model.Evento;
import dgtic.core.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/eventos")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @GetMapping
    public String mostrarEventos(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(name = "categoria", required = false) Integer categoriaId,
                                 Model model) {
        Pageable pageable = PageRequest.of(page, 6);
        Page<Evento> eventosPage;

        if (categoriaId != null) {
            eventosPage = eventoService.obtenerPorCategoriaPaginado(categoriaId, pageable);
        } else {
            eventosPage = eventoService.obtenerTodosPaginado(pageable);
        }

        model.addAttribute("eventos", eventosPage);
        model.addAttribute("categoriaSeleccionada", categoriaId);
        return "principal/eventos";
    }







}
