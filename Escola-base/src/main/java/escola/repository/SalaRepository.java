package escola.repository;

import escola.model.Sala;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaRepository extends JpaRepository<Sala, Long> {

    boolean existsByCodigoIgnoreCase(String codigo);
}
