package dgtic.core.controller;

import dgtic.core.model.Boleto;
import dgtic.core.model.Devolucion;
import dgtic.core.model.Usuario;
import dgtic.core.service.BoletoService;
import dgtic.core.service.DevolucionService;
import dgtic.core.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/usuarios")
public class AdminUsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private BoletoService boletoService;

    @Autowired
    private DevolucionService devolucionService;

    @GetMapping
    public String listarUsuarios(Model model) {
        List<Usuario> usuarios = usuarioService.obtenerTodos();
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("titulo", "Gestión de Usuarios");
        return "admin/usuarios_admin";
    }

    @GetMapping("/ver/{id}")
    public String verUsuario(@PathVariable("id") Integer id, Model model) {
        Usuario usuario = usuarioService.buscarPorId(id);
        if (usuario == null) {
            return "redirect:/admin/usuarios";
        }

        List<Boleto> boletos = boletoService.obtenerBoletosPorUsuario(usuario);

        model.addAttribute("usuario", usuario);
        model.addAttribute("boletos", boletos);

        return "admin/ver_usuario";
    }
}
