package dgtic.core.controller;

import dgtic.core.model.TipoEvento;
import dgtic.core.service.TipoEventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/categorias")
public class TipoEventoController {
    @Autowired
    private TipoEventoService tipoEventoService;

    @GetMapping
    public String mostrarCategorias(Model model) {
        model.addAttribute("categorias", tipoEventoService.obtenerTodos());
        return "principal/categorias";
    }
}

