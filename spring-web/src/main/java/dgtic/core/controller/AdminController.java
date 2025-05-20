// AdminController.java
package dgtic.core.controller;

import dgtic.core.model.Usuario;
import dgtic.core.service.EventoService;
import dgtic.core.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/inicio")
    public String inicioAdmin(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            String correo = authentication.getName();
            Usuario admin = usuarioService.buscarPorCorreo(correo);
            model.addAttribute("admin", admin);
        }
        return "admin/inicio_admin";
    }

}
