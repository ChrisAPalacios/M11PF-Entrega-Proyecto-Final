package dgtic.core.controller;

import dgtic.core.model.Colonia;
import dgtic.core.service.ColoniaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ColoniaController {

    @Autowired
    private ColoniaService coloniaService;

    @GetMapping("/colonias")
    public List<Colonia> obtenerColoniasPorAlcaldia(@RequestParam("alcaldiaId") Integer idAlcaldia) {
        return coloniaService.buscarPorAlcaldia(idAlcaldia);
    }
}
