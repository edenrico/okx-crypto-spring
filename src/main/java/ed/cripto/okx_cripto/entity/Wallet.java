package ed.cripto.okx_cripto.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import ed.cripto.okx_cripto.entity.User;
import java.util.ArrayList;
import java.util.List;
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
    private Double xrpBalance; // Novo campo para XRP

    @OneToOne(mappedBy = "wallet")
    @JsonBackReference
    private User user;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CriptoCurrency> criptosCompradas = new ArrayList<>();

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

    public Double getXrpBalance() {
        return xrpBalance;
    }

    public void setXrpBalance(Double xrpBalance) {
        this.xrpBalance = xrpBalance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<CriptoCurrency> getCriptosCompradas() {
        return criptosCompradas;
    }

    // Método simples para adicionar ao histórico (a lógica pode ser aprimorada para consolidar compras)
    public void addCripto(CriptoCurrency cripto, double quantidade) {
        cripto.setPrecoAtual(cripto.getPrecoAtual() * quantidade);
        this.criptosCompradas.add(cripto);
    }
}
