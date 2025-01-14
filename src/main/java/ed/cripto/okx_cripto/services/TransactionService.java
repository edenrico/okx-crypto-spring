package ed.cripto.okx_cripto.services;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ed.cripto.okx_cripto.entity.Transaction;
import ed.cripto.okx_cripto.entity.Wallet;
import ed.cripto.okx_cripto.entity.CriptoCurrency;
import ed.cripto.okx_cripto.repository.TransactionRepository;
import ed.cripto.okx_cripto.repository.WalletRepository;
import ed.cripto.okx_cripto.repository.CriptoCurrencyRepository;
import java.util.Date;

@Service
@Transactional
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private CriptoCurrencyRepository criptoCurrencyRepository;

    public Transaction createTransaction(UUID walletId, UUID criptoId, String tipo, Double quantidade, Double preco) {
        Wallet wallet = walletRepository.findById(walletId).orElse(null);
        CriptoCurrency criptoCurrency = criptoCurrencyRepository.findById(criptoId).orElse(null);
        if (wallet != null && criptoCurrency != null) {
            Transaction transacao = new Transaction();
            transacao.setWallet(wallet);
            transacao.setCriptoCurrency(criptoCurrency);
            transacao.setTipo(tipo);
            transacao.setQuantidade(quantidade);
            transacao.setPreco(preco);
            transacao.setTimestamp(new Date());
            return transactionRepository.save(transacao);
        }
        return null;
    }

    public List<Transaction> getTransactionsByWalletId(UUID walletId) {
        return transactionRepository.findByWalletKeyId(walletId);
    }

    public void deleteTransaction(UUID transactionId) {
        transactionRepository.deleteById(transactionId);
    }
}
