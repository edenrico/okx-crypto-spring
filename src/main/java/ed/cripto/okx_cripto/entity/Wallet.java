package ed.cripto.okx_cripto.entity;

import jakarta.persistence.*;
import ed.cripto.okx_cripto.entity.User;

import java.util.UUID;

@Entity
@Table(name = "wallet")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID keyId;

    private Double bitcoinBalance;
    private Double dogecoinBalance;
    private Double usdBalance;

    @OneToOne(mappedBy = "wallet")
    private User user;

    // Getters e Setters
    public UUID getKeyId() {
        return keyId;
    }

    public void setKeyId(UUID keyId) {
        this.keyId = keyId;
    }

    public Double getBitcoinBalance() {
        return bitcoinBalance;
    }

    public void setBitcoinBalance(Double bitcoinBalance) {
        this.bitcoinBalance = bitcoinBalance;
    }

    public Double getDogecoinBalance() {
        return dogecoinBalance;
    }

    public void setDogecoinBalance(Double dogecoinBalance) {
        this.dogecoinBalance = dogecoinBalance;
    }

    public Double getUsdBalance() {
        return usdBalance;
    }

    public void setUsdBalance(Double usdBalance) {
        this.usdBalance = usdBalance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
