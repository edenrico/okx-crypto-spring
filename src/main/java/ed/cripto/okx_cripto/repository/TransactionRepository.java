package ed.cripto.okx_cripto.repository;

import ed.cripto.okx_cripto.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    List<Transaction> findByWalletKeyId(UUID walletId);
}
