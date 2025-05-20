package dgtic.core.controller;

import dgtic.core.model.*;
import dgtic.core.repository.BoletoRepository;
import dgtic.core.repository.DevolucionRepository;
import dgtic.core.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private TipoEventoService tipoEventoService;

    @Autowired
    private EventoService eventoService;

    @Autowired
    private AsientoService asientoService;

    @Autowired
    private ZonaService zonaService;

    @Autowired
    private AsientoEventoService asientoEventoService;

    @Autowired
    private BoletoRepository boletoRepository;

    @Autowired
    private  BoletoService boletoService;

    @Autowired
    private DevolucionRepository devolucionRepository;

    @Autowired
    private DevolucionService devolucionService;


    @GetMapping("/inicio")
    public String inicioCliente(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("usuarioAutenticado", authentication.getName());
        }
        model.addAttribute("eventosDestacados", eventoService.obtenerEventosAleatorios(5));

        return "cliente/inicio_cliente";
    }

    @GetMapping("/eventos")
    public String eventosCliente(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(name = "categoria", required = false) Integer categoriaId,
                                 Model model) {
        Pageable pageable = PageRequest.of(page, 6);
        Page<Evento> eventosPage;

        if (categoriaId != null) {
            eventosPage = eventoService.obtenerPorCategoriaPaginado(categoriaId, pageable);
        } else {
            eventosPage = eventoService.obtenerTodosPaginado(pageable);
        }

        model.addAttribute("eventos", eventosPage.getContent());
        model.addAttribute("totalPages", eventosPage.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("categoriaSeleccionada", categoriaId);
        return "cliente/eventos_cliente";
    }


    @GetMapping("/categorias")
    public String categoriasCliente(Model model) {
        model.addAttribute("titulo", "Categorías - Eventia");
        model.addAttribute("categorias", tipoEventoService.obtenerTodos());
        return "/cliente/categorias_cliente";
    }

    @GetMapping("/perfil")
    public String perfilCliente(Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) return "redirect:/login";
        model.addAttribute("usuario", usuario);
        return "cliente/perfil_cliente";
    }

    @GetMapping("/mis-boletos")
    public String boletosCliente(@RequestParam(defaultValue = "0") int page, Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) return "redirect:/login";

        Pageable pageable = PageRequest.of(page, 5);
        Page<Boleto> boletos = boletoRepository
                .findByUsuario_IdUsuarioAndEstadoNotIn(
                        usuario.getIdUsuario(),
                        List.of("Disponible"),
                        pageable
                );

        model.addAttribute("boletos", boletos.getContent());
        model.addAttribute("totalPages", boletos.getTotalPages());
        model.addAttribute("currentPage", page);
        return "cliente/mis_boletos";
    }

    @GetMapping("/eventos/{id}")
    public String mostrarCompraBoletos(@PathVariable("id") Integer idEvento, Model model) {
        Evento evento = eventoService.findById(idEvento);
        List<AsientoEvento> asientoEventos = asientoEventoService.obtenerDisponiblesPorEvento(idEvento);
        Set<Zona> zonas = asientoEventos.stream()
                .map(ae -> ae.getAsiento().getZona())
                .collect(Collectors.toSet());
        model.addAttribute("evento", evento);
        model.addAttribute("zonas", zonas);
        return "cliente/comprar_boletos";
    }

    @GetMapping("/asientos-disponibles")
    @ResponseBody
    public List<AsientoEvento> obtenerAsientosDisponiblesPorZonaYEvento(
            @RequestParam("idZona") Integer idZona,
            @RequestParam("idEvento") Integer idEvento) {
        return asientoEventoService.buscarDisponiblesPorZonaYEvento(idZona, idEvento);
    }

    @GetMapping("/eventos/{id}/seleccion")
    public String seleccionarZonaYMostrarAsientos(
            @PathVariable("id") Integer idEvento,
            @RequestParam("idZona") Integer idZona,
            @RequestParam("cantidad") Integer cantidad,
            Model model) {
        Evento evento = eventoService.findById(idEvento);
        List<AsientoEvento> asientoEventos = asientoEventoService.obtenerDisponiblesPorEvento(idEvento);
        Set<Zona> zonas = asientoEventos.stream()
                .map(ae -> ae.getAsiento().getZona())
                .collect(Collectors.toSet());
        List<AsientoEvento> asientosZonaEvento = asientoEventoService.obtenerPorEventoYZona(idEvento, idZona);
        model.addAttribute("evento", evento);
        model.addAttribute("zonas", zonas);
        model.addAttribute("cantidad", cantidad);
        model.addAttribute("idZona", idZona);
        model.addAttribute("asientosDisponibles", asientosZonaEvento);
        return "cliente/comprar_boletos";
    }

    @GetMapping("/pagos")
    public String pagosCliente(HttpSession session, Model model) {
        List<AsientoEvento> canasta = (List<AsientoEvento>) session.getAttribute("canasta");
        if (canasta == null) {
            canasta = new ArrayList<>();
        }

        double total = canasta.stream()
                .mapToDouble(ae -> ae.getAsiento().getZona().getPrecio())
                .sum();

        model.addAttribute("canasta", canasta);
        model.addAttribute("total", total);
        model.addAttribute("titulo", "Confirmación de Compra");
        return "cliente/pagos";
    }


    @PostMapping("/agregar-a-canasta")
    public String agregarACanasta(
            @RequestParam("asientosSeleccionados") List<Integer> asientosSeleccionados,
            @RequestParam("idEvento") Integer idEvento,
            @RequestParam("idZona") Integer idZona,
            @RequestParam("cantidad") Integer cantidad,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        List<AsientoEvento> canasta = (List<AsientoEvento>) session.getAttribute("canasta");
        if (canasta == null) {
            canasta = new ArrayList<>();
        }

        boolean huboDuplicado = false;

        for (Integer idAsiento : asientosSeleccionados) {
            AsientoEventoId aeId = new AsientoEventoId(idEvento, idAsiento);
            AsientoEvento asientoEvento = asientoEventoService.buscarPorIdSimple(aeId);

            boolean yaExiste = canasta.stream().anyMatch(ae -> ae.getId().equals(aeId));

            if (asientoEvento != null && "DISPONIBLE".equalsIgnoreCase(asientoEvento.getEstado())) {
                if (!yaExiste) {
                    canasta.add(asientoEvento);
                } else {
                    huboDuplicado = true;
                }
            }
        }

        session.setAttribute("canasta", canasta);

        if (huboDuplicado) {
            redirectAttributes.addFlashAttribute("mensaje",
                    "Algunos asientos ya estaban en la canasta y no fueron agregados nuevamente.");
        }

        return "redirect:/cliente/canasta";
    }

    @GetMapping("/canasta")
    public String verCanasta(HttpSession session, Model model) {
        List<AsientoEvento> canasta = (List<AsientoEvento>) session.getAttribute("canasta");
        if (canasta == null) {
            canasta = new ArrayList<>();
        }
        model.addAttribute("canasta", canasta);
        return "cliente/canasta";
    }



    @GetMapping("/canasta/eliminar/{index}")
    public String eliminarDeCanasta(@PathVariable int index, HttpSession session) {
        List<AsientoEvento> canasta = (List<AsientoEvento>) session.getAttribute("canasta");
        if (canasta != null && index >= 0 && index < canasta.size()) {
            canasta.remove(index);
        }
        return "redirect:/cliente/canasta";
    }

    @PostMapping("/devolver-boleto")
    public String devolverBoleto(
            @RequestParam("idBoleto") Integer idBoleto,
            @RequestParam("motivo") String motivo,
            HttpSession session,
            RedirectAttributes redirect) {

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return "redirect:/login";
        }

        Boleto boleto = boletoService.buscarPorId(idBoleto);

        if (boleto != null &&
                boleto.getUsuario().getIdUsuario().equals(usuario.getIdUsuario()) &&
                boleto.getEstado().equalsIgnoreCase("Vendido")) {

            // Verificar si ya tiene devolución
            if (boleto.getDevolucion() == null) {

                // Crear nueva devolución
                Devolucion devolucion = new Devolucion();
                devolucion.setFechaDevolucion(LocalDateTime.now());
                devolucion.setMotivo(motivo);
                devolucion.setCompra(boleto.getCompra());

                devolucion = devolucionService.guardar(devolucion);

                boleto.setEstado("Disponible");
                boleto.setDevolucion(devolucion);
                boletoService.guardar(boleto);

                // Liberar el asiento
                AsientoEvento asientoEvento = boleto.getAsientoEvento();
                asientoEvento.setEstado("DISPONIBLE");
                asientoEventoService.guardar(asientoEvento);

                redirect.addFlashAttribute("mensaje", "Boleto devuelto exitosamente. El asiento ha sido liberado.");
            } else {
                redirect.addFlashAttribute("error", "Este boleto ya fue devuelto anteriormente.");
            }

        } else {
            redirect.addFlashAttribute("error", "No se puede devolver este boleto.");
        }

        return "redirect:/cliente/mis-boletos";
    }






}
