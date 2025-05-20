package dgtic.core.controller;

import dgtic.core.model.*;
import dgtic.core.repository.AlcaldiaRepository;
import dgtic.core.repository.ColoniaRepository;
import dgtic.core.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private DireccionService direccionService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private AlcaldiaRepository alcaldiaRepository;

    @Autowired
    private ColoniaRepository coloniaRepository;

    @GetMapping("/registro")
    public String mostrarRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("direccion", new Direccion());
        model.addAttribute("metodoPago", "CREDITO");
        model.addAttribute("alcaldias", alcaldiaRepository.findAll());
        model.addAttribute("colonias", coloniaRepository.findAll());
        return "registro/registro";
    }

    @PostMapping("/registro")
    public String registrarUsuario(
            @Valid @ModelAttribute("usuario") Usuario usuario,
            BindingResult resultUsuario,
            @RequestParam("calle") String calle,
            @RequestParam("numExt") String numExt,
            @RequestParam(value = "numInt", required = false) String numInt,
            @RequestParam("idAlcaldia") Integer idAlcaldia,
            @RequestParam("metodoPago") String metodoPago,
            Model model
    ) {
        if (resultUsuario.hasErrors()) {
            model.addAttribute("alcaldias", alcaldiaRepository.findAll());
            model.addAttribute("colonias", coloniaRepository.findAll());
            return "registro/registro";
        }

        if (!usuarioService.registrarUsuario(usuario)) {
            model.addAttribute("error", "Este correo ya está registrado.");
            model.addAttribute("alcaldias", alcaldiaRepository.findAll());
            model.addAttribute("colonias", coloniaRepository.findAll());
            return "registro/registro";
        }
        Direccion direccion = new Direccion();
        direccion.setCalle(calle);
        direccion.setNumExt(numExt);
        direccion.setNumInt(numInt);
        direccion.setUsuario(usuario);
        direccion.setAlcaldia(alcaldiaRepository.findById(idAlcaldia).orElse(null));
        direccionService.guardar(direccion);
        Cliente cliente = new Cliente();
        cliente.setUsuario(usuario);
        cliente.setFechaRegistro(LocalDate.now());
        cliente.setMetodoPago(Cliente.MetodoPago.valueOf(metodoPago));
        clienteService.guardar(cliente);

        return "redirect:/login";
    }
}
