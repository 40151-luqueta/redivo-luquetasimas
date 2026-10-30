package escola.service;

import escola.model.Sala;
import escola.model.StatusSala;
import escola.model.TipoSala;
import escola.repository.SalaRepository;
import java.util.List;
import java.util.Locale;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SalaService {

    private final SalaRepository salaRepository;

    public SalaService(SalaRepository salaRepository) {
        this.salaRepository = salaRepository;
    }

    @Transactional
    public Sala cadastrar(Sala sala) {
        sala.setCodigo(sala.getCodigo().trim().toUpperCase(Locale.ROOT));
        sala.setNome(sala.getNome().trim());

        if (sala.getTipo() == null) {
            sala.setTipo(TipoSala.SALA_AULA);
        }

        if (salaRepository.existsByCodigoIgnoreCase(sala.getCodigo())) {
            throw new RegraDeNegocioException("Já existe uma sala cadastrada com esse código");
        }

        sala.setId(null);
        sala.setStatus(StatusSala.LIVRE);

        return salaRepository.save(sala);
    }

    @Transactional(readOnly = true)
    public List<Sala> listarTodas() {
        return salaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Sala buscarPorId(Long id) {
        return salaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Sala não encontrada"));
    }

    @Transactional
    public Sala reservar(Long id) {
        Sala sala = buscarPorId(id);

        if (sala.getStatus() != StatusSala.LIVRE) {
            throw new RegraDeNegocioException("A sala já está reservada");
        }

        sala.setStatus(StatusSala.RESERVADA);
        return salaRepository.save(sala);
    }

    @Transactional
    public Sala liberar(Long id) {
        Sala sala = buscarPorId(id);

        if (sala.getStatus() != StatusSala.RESERVADA) {
            throw new RegraDeNegocioException("Só é possível liberar uma sala que está reservada");
        }

        sala.setStatus(StatusSala.LIVRE);
        return salaRepository.save(sala);
    }

    @Transactional
    public void excluir(Long id) {
        Sala sala = buscarPorId(id);

        if (sala.getStatus() == StatusSala.RESERVADA) {
            throw new RegraDeNegocioException("Não é possível excluir uma sala reservada");
        }

        salaRepository.delete(sala);
    }
}
