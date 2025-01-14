package ed.cripto.okx_cripto.repository;

import ed.cripto.okx_cripto.entity.CriptoCurrency;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface CriptoCurrencyRepository extends JpaRepository<CriptoCurrency, UUID> {
    // Métodos CRUD já estão disponíveis a partir de JpaRepository
}
