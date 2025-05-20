package dgtic.core.service;

import dgtic.core.model.Boleto;
import dgtic.core.model.Devolucion;
import dgtic.core.model.Usuario;
import dgtic.core.repository.BoletoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoletoService {

    @Autowired
    private BoletoRepository boletoRepository;

    public List<Boleto> obtenerTodos() {
        return boletoRepository.findAll();
    }

    public Boleto guardar(Boleto boleto) {
        return boletoRepository.save(boleto);
    }

    public Boleto buscarPorId(Integer id) {
        Optional<Boleto> optional = boletoRepository.findById(id);
        return optional.orElse(null);
    }

    public void eliminar(Integer id) {
        boletoRepository.deleteById(id);
    }

    public List<Boleto> obtenerBoletosPorUsuario(Usuario usuario) {
        return boletoRepository.findByUsuario_IdUsuario(usuario.getIdUsuario());
    }



}
