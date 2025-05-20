package dgtic.core.controller;

import dgtic.core.model.Usuario;
import dgtic.core.security.JwtUtil;
import dgtic.core.service.UsuarioService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/login")
    public String mostrarLogin(Model model) {
        model.addAttribute("correo", "");
        return "principal/login";
    }

    @PostMapping("/login-inteligente")
    public String procesarLoginInteligente(@RequestParam String correo,
                                           @RequestParam String password,
                                           Model model,
                                           HttpSession session,
                                           HttpServletResponse response) {
        Usuario usuario = usuarioService.buscarPorCorreo(correo);

        if (usuario == null) {
            model.addAttribute("error", "Correo o contraseña incorrectos");
            model.addAttribute("correo", correo);
            return "principal/login";
        }

        boolean esAdmin = "ADMINI".equalsIgnoreCase(usuario.getTipoUsuario());
        boolean accesoValido;

        if (esAdmin) {
            accesoValido = password.equals(usuario.getPassword());
        } else {
            accesoValido = passwordEncoder.matches(password, usuario.getPassword());
        }

        if (!accesoValido) {
            model.addAttribute("error", "Correo o contraseña incorrectos");
            model.addAttribute("correo", correo);
            return "principal/login";
        }
        String rolConPrefijo = "ROLE_" + usuario.getTipoUsuario();
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(rolConPrefijo));
        Authentication auth = new UsernamePasswordAuthenticationToken(
                usuario.getCorreo(), null, authorities
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
        session.setAttribute("usuario", usuario);
        String jwt = jwtUtil.generateToken(usuario.getCorreo(), rolConPrefijo);
        Cookie cookie = new Cookie("jwt", jwt);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);

        return esAdmin ? "redirect:/admin/inicio" : "redirect:/cliente/inicio";
    }
}
