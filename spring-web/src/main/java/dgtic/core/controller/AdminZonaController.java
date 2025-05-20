package dgtic.core.controller;

import dgtic.core.model.Zona;
import dgtic.core.service.AsientoEventoService;
import dgtic.core.service.ZonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/zonas")
public class AdminZonaController {

    @Autowired
    private ZonaService zonaService;

    @Autowired
    private AsientoEventoService asientoEventoService;

    @GetMapping("/nueva")
    public String nuevaZona(Model model) {
        model.addAttribute("titulo", "Nueva Zona");
        model.addAttribute("zona", new Zona());
        return "admin/form_zona";
    }

    @PostMapping("/guardar")
    public String guardarZona(@ModelAttribute Zona zona, RedirectAttributes redirectAttributes) {
        zonaService.guardar(zona);
        redirectAttributes.addFlashAttribute("mensaje", "Zona guardada correctamente.");
        return "redirect:/admin/zonas";
    }

    @GetMapping("/editar/{id}")
    public String editarZona(@PathVariable("id") Integer id, Model model) {
        Zona zona = zonaService.buscarPorId(id);
        if (zona == null) {
            return "redirect:/admin/zonas";
        }

        model.addAttribute("titulo", "Editar Zona");
        model.addAttribute("zona", zona);
        return "admin/form_zona";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarZona(@PathVariable Integer id) {
        zonaService.eliminar(id);
        return "redirect:/admin/zonas";
    }

    @GetMapping
    public String listarZonas(Model model) {
        List<Zona> zonas = zonaService.obtenerTodas();
        Map<Integer, Boolean> asientosGenerados = new HashMap<>();

        for (Zona zona : zonas) {
            boolean generados = !asientoEventoService.buscarPorZona(zona.getIdZona()).isEmpty();
            asientosGenerados.put(zona.getIdZona(), generados);
        }

        model.addAttribute("zonas", zonas);
        model.addAttribute("asientosGenerados", asientosGenerados);
        model.addAttribute("titulo", "Gestión de Zonas");
        return "admin/zonas_admin";
    }
}
