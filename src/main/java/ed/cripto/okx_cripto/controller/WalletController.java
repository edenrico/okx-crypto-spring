package ed.cripto.okx_cripto.controller;

import ed.cripto.okx_cripto.entity.Wallet;
import ed.cripto.okx_cripto.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;

    // Agora usando walletId no lugar de userId
    @GetMapping("/balance/{walletId}")
    public ResponseEntity<Wallet> getBalance(@PathVariable UUID walletId) {
        Wallet wallet = walletService.getBalance(walletId);
        return ResponseEntity.ok(wallet);
    }

    @PostMapping("/add-funds/{walletId}")
    public ResponseEntity<Wallet> addFunds(@PathVariable UUID walletId, @RequestParam double amount, @RequestParam String currency) {
        Wallet wallet = walletService.addFunds(walletId, amount, currency);
        return ResponseEntity.ok(wallet);
    }

    @PostMapping("/transfer")
    public ResponseEntity<Boolean> transferFunds(@RequestParam UUID fromWalletId, @RequestParam UUID toWalletId, @RequestParam double amount, @RequestParam String currency) {
        boolean success = walletService.transferFunds(fromWalletId, toWalletId, amount, currency);
        return ResponseEntity.ok(success);
    }

    @GetMapping("/transactions/{walletId}")
    public ResponseEntity<List<ed.cripto.okx_cripto.entity.Transaction>> getTransactionHistory(@PathVariable UUID walletId) {
        List<ed.cripto.okx_cripto.entity.Transaction> transactions = walletService.getTransactionHistory(walletId);
        return ResponseEntity.ok(transactions);
    }
}
