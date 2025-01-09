package ed.cripto.okx_cripto.repository;

import ed.cripto.okx_cripto.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface WalletRepository extends JpaRepository<Wallet, UUID> {
}
