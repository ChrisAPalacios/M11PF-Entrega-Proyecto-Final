package dgtic.core.controller;

import dgtic.core.model.*;
import dgtic.core.service.*;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/cliente")
public class CompraController {

    @Autowired
    private EventoService eventoService;

    @Autowired
    private AsientoService asientoService;

    @Autowired
    private CompraService compraService;

    @Autowired
    private BoletoService boletoService;

    @Autowired
    private AsientoEventoService asientoEventoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PdfGeneratorService pdfGeneratorService;

    @PostMapping("/finalizar")
    public String finalizarCompra(HttpSession session, Model model, RedirectAttributes redirect) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            redirect.addFlashAttribute("error", "Debes iniciar sesión para finalizar la compra.");
            return "redirect:/login";
        }

        List<AsientoEvento> canasta = (List<AsientoEvento>) session.getAttribute("canasta");
        if (canasta == null || canasta.isEmpty()) {
            redirect.addFlashAttribute("error", "No hay boletos en la canasta.");
            return "redirect:/cliente/canasta";
        }

        Compra compra = new Compra();
        compra.setUsuario(usuario);
        compra.setFechaCompra(LocalDateTime.now());
        compra.setMontoTotal(0.0);
        compra = compraService.guardar(compra);

        double total = 0.0;
        for (AsientoEvento asientoEvento : canasta) {
            if ("DISPONIBLE".equalsIgnoreCase(asientoEvento.getEstado())) {
                Boleto boleto = new Boleto();
                boleto.setEstado("VENDIDO");
                boleto.setUsuario(usuario);
                boleto.setAsientoEvento(asientoEvento);
                boleto.setCompra(compra);
                boletoService.guardar(boleto);

                asientoEvento.setEstado("OCUPADO");
                asientoEventoService.guardar(asientoEvento);

                total += asientoEvento.getAsiento().getZona().getPrecio();
            }
        }
        compra.setMontoTotal(total);
        compra = compraService.guardar(compra);
        session.removeAttribute("canasta");
        session.setAttribute("ultimaCompra", compra);
        return "cliente/procesando";
    }


    @GetMapping("/descargar-pdf")
    public void descargarPdf(HttpServletResponse response, HttpSession session) throws Exception {
        Compra compraGuardada = (Compra) session.getAttribute("ultimaCompra");

        if (compraGuardada == null || compraGuardada.getIdCompra() == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Compra no encontrada en sesión.");
            return;
        }
        Compra compra = compraService.obtenerCompraConBoletos(compraGuardada.getIdCompra());

        if (compra == null || compra.getBoletos() == null || compra.getBoletos().isEmpty()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "No se encontraron boletos para la compra.");
            return;
        }

        byte[] pdfBytes = pdfGeneratorService.generarBoletosPdf(compra.getBoletos());

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=boletos.pdf");
        response.getOutputStream().write(pdfBytes);
        response.getOutputStream().flush();
    }
}
