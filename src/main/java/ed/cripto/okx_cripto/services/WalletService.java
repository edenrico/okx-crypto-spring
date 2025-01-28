package ed.cripto.okx_cripto.services;

import java.util.List;
import java.util.UUID;

import ed.cripto.okx_cripto.entity.CriptoCurrency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ed.cripto.okx_cripto.entity.Wallet;
import ed.cripto.okx_cripto.services.CoinGeckoService;
import ed.cripto.okx_cripto.repository.WalletRepository;

@Service
@Transactional
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private CoinGeckoService coinGeckoService;

    // Retorna o saldo da carteira
    public Wallet getBalance(UUID walletId) {
        return walletRepository.findById(walletId).orElse(null);
    }

    // Adiciona fundos à carteira
    public Wallet addFunds(UUID walletId, double amount, String currency) {
        Wallet wallet = walletRepository.findById(walletId).orElse(null);
        if (wallet != null) {
            if ("USD".equalsIgnoreCase(currency)) {
                wallet.setUsdBalance(wallet.getUsdBalance() + amount);
            }
            walletRepository.save(wallet);
        }
        return wallet;
    }

    // Compra criptomoedas
    public Wallet buyCripto(UUID walletId, String criptoNome, double quantidade) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new IllegalArgumentException("Carteira não encontrada"));

        // Obtém os preços das criptos
        List<CriptoCurrency> livePrices = coinGeckoService.getLivePrices();
        CriptoCurrency criptoAtual = livePrices.stream()
                .filter(cripto -> cripto.getNome().equalsIgnoreCase(criptoNome))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Criptomoeda não encontrada"));

        double precoAtual = criptoAtual.getPrecoAtual();
        double custoTotal = precoAtual * quantidade;

        // Verifica saldo
        if (wallet.getUsdBalance() < custoTotal) {
            throw new IllegalArgumentException("Saldo insuficiente");
        }

        // Deduz saldo e adiciona cripto
        wallet.setUsdBalance(wallet.getUsdBalance() - custoTotal);
        CriptoCurrency criptoComprada = new CriptoCurrency(null, criptoNome, precoAtual);
        wallet.addCripto(criptoComprada, quantidade);

        return walletRepository.save(wallet);
    }
}
