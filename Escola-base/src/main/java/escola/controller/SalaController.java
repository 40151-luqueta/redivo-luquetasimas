package escola.controller;

import escola.model.Sala;
import escola.service.SalaService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/salas")
public class SalaController {

    private final SalaService salaService;

    public SalaController(SalaService salaService) {
        this.salaService = salaService;
    }

    @PostMapping
    public ResponseEntity<Sala> cadastrar(@Valid @RequestBody Sala sala) {
        Sala salaCadastrada = salaService.cadastrar(sala);
        URI localizacao = URI.create("/api/salas/" + salaCadastrada.getId());
        return ResponseEntity.created(localizacao).body(salaCadastrada);
    }

    @GetMapping
    public ResponseEntity<List<Sala>> listarTodas() {
        return ResponseEntity.ok(salaService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sala> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(salaService.buscarPorId(id));
    }

    @PostMapping("/{id}/reservar")
    public ResponseEntity<Sala> reservar(@PathVariable Long id) {
        return ResponseEntity.ok(salaService.reservar(id));
    }

    @PostMapping("/{id}/liberar")
    public ResponseEntity<Sala> liberar(@PathVariable Long id) {
        return ResponseEntity.ok(salaService.liberar(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        salaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
