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

    // Obtém o saldo da carteira
    @GetMapping("/balance/{walletId}")
    public ResponseEntity<Wallet> getBalance(@PathVariable UUID walletId) {
        Wallet wallet = walletService.getBalance(walletId);
        return wallet != null ? ResponseEntity.ok(wallet) : ResponseEntity.notFound().build();
    }

    // Adiciona fundos à carteira
    @PostMapping("/add-funds/{walletId}")
    public ResponseEntity<Wallet> addFunds(
            @PathVariable UUID walletId,
            @RequestParam double amount,
            @RequestParam String currency
    ) {
        Wallet wallet = walletService.addFunds(walletId, amount, currency);
        return wallet != null ? ResponseEntity.ok(wallet) : ResponseEntity.badRequest().build();
    }

    // Compra criptomoedas
    @PostMapping("/{walletId}/buy-crypto")
    public ResponseEntity<Wallet> buyCripto(
            @PathVariable UUID walletId,
            @RequestParam String criptoNome,
            @RequestParam double quantidade
    ) {
        try {
            Wallet updatedWallet = walletService.buyCripto(walletId, criptoNome, quantidade);
            return ResponseEntity.ok(updatedWallet);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}