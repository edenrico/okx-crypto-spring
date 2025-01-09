package ed.cripto.okx_cripto.controller;


import ed.cripto.okx_cripto.entity.Wallet;
import ed.cripto.okx_cripto.services.WalletService;
import jakarta.transaction.Transaction;
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

    @GetMapping("/balance/{userId}")
    public ResponseEntity<Wallet> getBalance(@PathVariable UUID userId) {
        Wallet wallet = walletService.getBalance(userId);
        return ResponseEntity.ok(wallet);
    }

    @PostMapping("/add-funds/{userId}")
    public ResponseEntity<Wallet> addFunds(@PathVariable UUID userId, @RequestParam double amount, @RequestParam String currency) {
        Wallet wallet = walletService.addFunds(userId, amount, currency);
        return ResponseEntity.ok(wallet);
    }

    @PostMapping("/transfer")
    public ResponseEntity<Boolean> transferFunds(@RequestParam UUID fromUserId, @RequestParam UUID toUserId, @RequestParam double amount, @RequestParam String currency) {
        boolean success = walletService.transferFunds(fromUserId, toUserId, amount, currency);
        return ResponseEntity.ok(success);
    }

    @GetMapping("/transactions/{userId}")
    public ResponseEntity<List<Transaction>> getTransactionHistory(@PathVariable UUID userId) {
        List<Transaction> transactions = walletService.getTransactionHistory(userId);
        return ResponseEntity.ok(transactions);
    }
}
