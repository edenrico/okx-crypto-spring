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

        // Obtém os preços das criptomoedas
        List<CriptoCurrency> livePrices = coinGeckoService.getLivePrices();
        CriptoCurrency criptoAtual = livePrices.stream()
                .filter(cripto -> cripto.getNome().equalsIgnoreCase(criptoNome))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Criptomoeda não encontrada"));

        double precoAtual = criptoAtual.getPrecoAtual();
        double custoTotal = precoAtual * quantidade;

        // Verifica se o saldo em USD é suficiente
        if (wallet.getUsdBalance() < custoTotal) {
            throw new IllegalArgumentException("Saldo insuficiente");
        }

        // Deduz o valor total do saldo em USD
        wallet.setUsdBalance(wallet.getUsdBalance() - custoTotal);

        if ("Bitcoin".equalsIgnoreCase(criptoNome)) {
            double saldoAtual = wallet.getBitcoinBalance() == null ? 0.0 : wallet.getBitcoinBalance();
            wallet.setBitcoinBalance(saldoAtual + quantidade);
        } else if ("Dogecoin".equalsIgnoreCase(criptoNome)) {
            double saldoAtual = wallet.getDogecoinBalance() == null ? 0.0 : wallet.getDogecoinBalance();
            wallet.setDogecoinBalance(saldoAtual + quantidade);
        } else if ("XRP".equalsIgnoreCase(criptoNome)) {
            double saldoXrp = wallet.getXrpBalance() == null ? 0.0 : wallet.getXrpBalance();
            wallet.setXrpBalance(saldoXrp + quantidade);
        }

        // Mantém o histórico de compra na lista de criptosCompradas
        CriptoCurrency criptoComprada = new CriptoCurrency(null, criptoNome, precoAtual);
        wallet.addCripto(criptoComprada, quantidade);

        return walletRepository.save(wallet);
    }
    // Venda criptomoedas
    public Wallet sellCripto(UUID walletId, String criptoNome, double quantidade) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new IllegalArgumentException("Carteira não encontrada"));

        // Obtém os preços das criptomoedas
        List<CriptoCurrency> livePrices = coinGeckoService.getLivePrices();
        CriptoCurrency criptoAtual = livePrices.stream()
                .filter(cripto -> cripto.getNome().equalsIgnoreCase(criptoNome))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Criptomoeda não encontrada"));
        double precoAtual = criptoAtual.getPrecoAtual();
        double valorVenda = precoAtual * quantidade;

        // Verifica se a carteira possui quantidade suficiente para vender
        if ("Bitcoin".equalsIgnoreCase(criptoNome)) {
            double saldoAtual = wallet.getBitcoinBalance() == null ? 0.0 : wallet.getBitcoinBalance();
            if (saldoAtual < quantidade) {
                throw new IllegalArgumentException("Quantidade insuficiente de Bitcoin para venda");
            }
            wallet.setBitcoinBalance(saldoAtual - quantidade);
        }
        else if ("Dogecoin".equalsIgnoreCase(criptoNome)) {
            double saldoAtual = wallet.getDogecoinBalance() == null ? 0.0 : wallet.getDogecoinBalance();
            if (saldoAtual < quantidade) {
                throw new IllegalArgumentException("Quantidade insuficiente de Dogecoin para venda");
            }
            wallet.setDogecoinBalance(saldoAtual - quantidade);
        }
        // Caso deseje implementar venda para outras criptomoedas, adicione a lógica correspondente
        else if ("XRP".equalsIgnoreCase(criptoNome)) {
            // Exemplo: se você tiver um campo específico ou gerenciar via lista de criptosCompradas
            throw new IllegalArgumentException("Venda de XRP não implementada");
        }
        else {
            throw new IllegalArgumentException("Criptomoeda não suportada para venda");
        }

        // Acrescenta o valor da venda ao saldo em USD
        wallet.setUsdBalance(wallet.getUsdBalance() + valorVenda);

        return walletRepository.save(wallet);
    }
}
