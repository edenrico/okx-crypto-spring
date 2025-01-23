package ed.cripto.okx_cripto.services;

import java.util.List;
import java.util.UUID;
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

    public Wallet getBalance(UUID walletId) {
        return walletRepository.findById(walletId).orElse(null);
    }

    public Wallet addFunds(UUID walletId, double amount, String currency) {
        Wallet wallet = walletRepository.findById(walletId).orElse(null);
        if (wallet != null) {
            // Atualiza saldo com base na moeda especificada
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

    public boolean transferFunds(UUID fromWalletId, UUID toWalletId, double amount, String currency) {
        Wallet fromWallet = walletRepository.findById(fromWalletId).orElse(null);
        Wallet toWallet = walletRepository.findById(toWalletId).orElse(null);
        if (fromWallet != null && toWallet != null) {
            // Lógica para transferir fundos entre carteiras
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

    public List<ed.cripto.okx_cripto.entity.Transaction> getTransactionHistory(UUID walletId) {
        // Implemente a lógica para recuperar o histórico de transações
        // Aqui pode ser um retorno do repository se você tiver configurado o relacionamento
        return null; // Placeholder para exemplo
    }
}
