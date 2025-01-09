package ed.cripto.okx_cripto.services;

import java.util.List;
import java.util.UUID;

import jakarta.transaction.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ed.cripto.okx_cripto.entity.Wallet;
import ed.cripto.okx_cripto.repository.WalletRepository;

@Service
@Transactional
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    public Wallet getBalance(UUID userId) {
        return walletRepository.findById(userId).orElse(null);
    }

    public Wallet addFunds(UUID userId, double amount, String currency) {
        Wallet wallet = walletRepository.findById(userId).orElse(null);
        if (wallet != null) {
            // Adicionar lógica para atualizar o saldo da carteira com a moeda específica
            switch (currency) {
                case "USD":
                    wallet.setUsdBalance(wallet.getUsdBalance() + amount);
                    break;
                case "BTC":
                    wallet.setBitcoinBalance(wallet.getBitcoinBalance() + amount);
                    break;
                case "DOGE":
                    wallet.setDogecoinBalance(wallet.getDogecoinBalance() + amount);
                    break;
            }
            walletRepository.save(wallet);
        }
        return wallet;
    }

    public boolean transferFunds(UUID fromUserId, UUID toUserId, double amount, String currency) {
        Wallet fromWallet = walletRepository.findById(fromUserId).orElse(null);
        Wallet toWallet = walletRepository.findById(toUserId).orElse(null);
        if (fromWallet != null && toWallet != null) {
            // lógica para transferir fundos entre carteiras
            switch (currency) {
                case "USD":
                    fromWallet.setUsdBalance(fromWallet.getUsdBalance() - amount);
                    toWallet.setUsdBalance(toWallet.getUsdBalance() + amount);
                    break;
                case "BTC":
                    fromWallet.setBitcoinBalance(fromWallet.getBitcoinBalance() - amount);
                    toWallet.setBitcoinBalance(toWallet.getBitcoinBalance() + amount);
                    break;
                case "DOGE":
                    fromWallet.setDogecoinBalance(fromWallet.getDogecoinBalance() - amount);
                    toWallet.setDogecoinBalance(toWallet.getDogecoinBalance() + amount);
                    break;
            }
            walletRepository.save(fromWallet);
            walletRepository.save(toWallet);
            return true;
        }
        return false;
    }

    public List<Transaction> getTransactionHistory(UUID userId) {
        // Adicionar lógica para recuperar o histórico de transações do usuário
        return null; // Placeholder
    }
}
