package dgtic.core.controller;

import dgtic.core.model.Evento;
import dgtic.core.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/")
public class InicioController {

    @Autowired
    private EventoService eventoService;

    @GetMapping("/")
    public String mostrarInicio(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            model.addAttribute("usuarioAutenticado", username);

            Collection<? extends GrantedAuthority> roles = authentication.getAuthorities();
            boolean esAdmin = roles.stream()
                    .anyMatch(r -> r.getAuthority().equals("ROLE_ADMINI"));
            boolean esCliente = roles.stream()
                    .anyMatch(r -> r.getAuthority().equals("ROLE_CLIENTE"));

            model.addAttribute("esAdmin", esAdmin);
            model.addAttribute("esCliente", esCliente);
        }

        model.addAttribute("eventosDestacados", eventoService.obtenerEventosAleatorios(5));
        return "inicio";
    }

    @GetMapping("/buscar-eventos")
    @ResponseBody
    public List<Evento> buscarEventosPorNombre(@RequestParam String nombre) {
        return eventoService.buscarPorNombre(nombre);
    }

    @GetMapping("/evento/{id}")
    @ResponseBody
    public Evento obtenerEvento(@PathVariable Integer id) {
        return eventoService.buscarPorId(id);
    }
}
