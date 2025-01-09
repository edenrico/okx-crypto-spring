package ed.cripto.okx_cripto.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

import java.util.UUID;

@Entity
public class Wallet {

    @Id
    private UUID keyId;

    private Double bitcoinBalance;
    private Double dogecoinBalance;
    private Double usdBalance;

    @OneToOne(mappedBy = "wallet")
    private User user;

    //@OneToMany(mappedBy = "wallet")
    //private List<Transaction> transactions;

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

    public void setDogecoinBalance(double v) {
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
