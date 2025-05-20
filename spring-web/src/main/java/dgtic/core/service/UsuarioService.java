package dgtic.core.service;

import dgtic.core.model.Usuario;
import dgtic.core.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario autenticarUsuario(String correo, String password) {
        Usuario usuario = usuarioRepository.findByCorreo(correo);
        if (usuario != null && "ADMINI".equalsIgnoreCase(usuario.getTipoUsuario())
                && usuario.getPassword().equals(password)) {
            return usuario;
        }
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCorreo(correo);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }

        if ("ADMINI".equalsIgnoreCase(usuario.getTipoUsuario())) {
            return org.springframework.security.core.userdetails.User
                    .withUsername(usuario.getCorreo())
                    .password("{noop}" + usuario.getPassword())
                    .roles(usuario.getTipoUsuario())
                    .build();
        } else {
            return org.springframework.security.core.userdetails.User
                    .withUsername(usuario.getCorreo())
                    .password(usuario.getPassword())
                    .roles(usuario.getTipoUsuario())
                    .build();
        }
    }

    public boolean registrarUsuario(Usuario usuario) {
        if (usuarioRepository.existsByCorreo(usuario.getCorreo())) {
            return false;
        }

        if ("CLIENTE".equalsIgnoreCase(usuario.getTipoUsuario())) {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }

        usuarioRepository.save(usuario);
        return true;
    }

    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }
    public Usuario buscarPorId(Integer id) {
        return usuarioRepository.findById(id).orElse(null);
    }
    public Usuario buscarPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo);
    }

}



