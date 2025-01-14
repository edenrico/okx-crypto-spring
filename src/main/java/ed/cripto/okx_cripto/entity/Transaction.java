package ed.cripto.okx_cripto.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import java.util.Date;
import java.util.UUID;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID transacaoId;

    private String tipo; // "compra" ou "venda"
    private Double quantidade;
    private Double preco;
    private Date timestamp; // Data e hora da transação

    @ManyToOne
    @JoinColumn(name = "keyId", referencedColumnName = "keyId")
    private Wallet wallet;

    @ManyToOne
    @JoinColumn(name = "idCripto", referencedColumnName = "idCripto")
    private CriptoCurrency criptoCurrency;

    // Construtor vazio
    public Transaction() {}

    // Construtor com parâmetros
    public Transaction(UUID transacaoId, String tipo, Double quantidade, Double preco, Date timestamp, Wallet wallet, CriptoCurrency criptoCurrency) {
        this.transacaoId = transacaoId;
        this.tipo = tipo;
        this.quantidade = quantidade;
        this.preco = preco;
        this.timestamp = timestamp;
        this.wallet = wallet;
        this.criptoCurrency = criptoCurrency;
    }

    // Getters e Setters

    public UUID getTransacaoId() {
        return transacaoId;
    }

    public void setTransacaoId(UUID transacaoId) {
        this.transacaoId = transacaoId;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public CriptoCurrency getCriptoCurrency() {
        return criptoCurrency;
    }

    public void setCriptoCurrency(CriptoCurrency criptoCurrency) {
        this.criptoCurrency = criptoCurrency;
    }
}
