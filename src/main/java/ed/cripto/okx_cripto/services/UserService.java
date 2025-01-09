package ed.cripto.okx_cripto.services;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ed.cripto.okx_cripto.entity.User;
import ed.cripto.okx_cripto.entity.Wallet;
import ed.cripto.okx_cripto.repository.UserRepository;
import ed.cripto.okx_cripto.repository.WalletRepository;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WalletRepository walletRepository;

    public User saveUser(User user) {
        Wallet wallet = new Wallet();
        wallet.setDogecoinBalance(wallet.getDogecoinBalance() + amount);
        wallet.setUsdBalance(0.0);
        wallet.setBitcoinBalance(0.0);
        walletRepository.save(wallet);

        user.setWallet(wallet);
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(UUID id) {
        return userRepository.findById(id).orElse(null);
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }
}
