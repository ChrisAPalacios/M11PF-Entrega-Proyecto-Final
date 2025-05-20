package dgtic.core.service;

import dgtic.core.model.Cliente;
import dgtic.core.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    private final ClienteRepository repo;

    public ClienteService(ClienteRepository repo) {
        this.repo = repo;
    }

    public Cliente guardar(Cliente cliente) {
        return repo.save(cliente);
    }

    public Optional<Cliente> buscarPorId(Integer id) {
        return repo.findById(id);
    }

    public void eliminar(Integer id) {
        repo.deleteById(id);
    }
}
