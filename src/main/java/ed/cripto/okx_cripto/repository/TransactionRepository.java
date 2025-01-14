package ed.cripto.okx_cripto.repository;

import ed.cripto.okx_cripto.entity.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface TransacaoRepository extends JpaRepository<Transacao, UUID> {
}
