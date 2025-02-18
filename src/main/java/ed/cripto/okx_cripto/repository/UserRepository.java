package ed.cripto.okx_cripto.repository;

import ed.cripto.okx_cripto.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);

    User findByWallet_KeyId(UUID walletId);
    
}
