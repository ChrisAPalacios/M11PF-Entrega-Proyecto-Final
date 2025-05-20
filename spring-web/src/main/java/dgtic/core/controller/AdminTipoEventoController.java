package dgtic.core.controller;

import dgtic.core.model.TipoEvento;
import dgtic.core.service.TipoEventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/tipos-evento")
public class AdminTipoEventoController {

    @Autowired
    private TipoEventoService tipoEventoService;

    @GetMapping
    public String listarTiposEvento(Model model) {
        model.addAttribute("tiposEvento", tipoEventoService.obtenerTodos());
        model.addAttribute("nuevoTipo", new TipoEvento()); // para formulario
        return "admin/tipos_evento";
    }

    @PostMapping("/agregar")
    public String agregarTipoEvento(@ModelAttribute TipoEvento tipoEvento, RedirectAttributes redirectAttributes) {
        try {
            tipoEventoService.guardar(tipoEvento);
            redirectAttributes.addFlashAttribute("exito", "Tipo de evento agregado correctamente.");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("error", "El tipo de evento ya existe.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ocurrió un error al agregar el tipo de evento.");
        }
        return "redirect:/admin/tipos-evento";
    }
}
