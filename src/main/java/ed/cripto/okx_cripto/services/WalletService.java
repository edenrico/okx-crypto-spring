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

    
    public Wallet getBalance(UUID walletId) {
        return walletRepository.findById(walletId).orElse(null);
    }

  
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

    
    public Wallet buyCripto(UUID walletId, String criptoNome, double quantidade) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new IllegalArgumentException("Carteira não encontrada"));

      
        List<CriptoCurrency> livePrices = coinGeckoService.getLivePrices();
        CriptoCurrency criptoAtual = livePrices.stream()
                .filter(cripto -> cripto.getNome().equalsIgnoreCase(criptoNome))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Criptomoeda não encontrada"));

        double precoAtual = criptoAtual.getPrecoAtual();
        double custoTotal = precoAtual * quantidade;

       
        if (wallet.getUsdBalance() < custoTotal) {
            throw new IllegalArgumentException("Saldo insuficiente");
        }

       
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

      
        CriptoCurrency criptoComprada = new CriptoCurrency(null, criptoNome, precoAtual);
        wallet.addCripto(criptoComprada, quantidade);

        return walletRepository.save(wallet);
    }
  
    public Wallet sellCripto(UUID walletId, String criptoNome, double quantidade) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new IllegalArgumentException("Carteira não encontrada"));

 
        List<CriptoCurrency> livePrices = coinGeckoService.getLivePrices();
        CriptoCurrency criptoAtual = livePrices.stream()
                .filter(cripto -> cripto.getNome().equalsIgnoreCase(criptoNome))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Criptomoeda não encontrada"));
        double precoAtual = criptoAtual.getPrecoAtual();
        double valorVenda = precoAtual * quantidade;

   
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
            
       // TESTE PARA XRP
        else if ("XRP".equalsIgnoreCase(criptoNome)) {
            throw new IllegalArgumentException("Venda de XRP não implementada");
        }
        else {
            throw new IllegalArgumentException("Criptomoeda não suportada para venda");
        }

        wallet.setUsdBalance(wallet.getUsdBalance() + valorVenda);

        return walletRepository.save(wallet);
    }
}
