package ed.cripto.okx_cripto.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "criptocurrency")
public class CriptoCurrency {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idCripto;

    private String nome; 
    private Double precoAtual; 

  
    public CriptoCurrency() {}

    
    public CriptoCurrency(UUID idCripto, String nome, Double precoAtual) {
        this.idCripto = idCripto;
        this.nome = nome;
        this.precoAtual = precoAtual;
    }

   

    public UUID getIdCripto() {
        return idCripto;
    }

    public void setIdCripto(UUID idCripto) {
        this.idCripto = idCripto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getPrecoAtual() {
        return precoAtual;
    }

    public void setPrecoAtual(Double precoAtual) {
        this.precoAtual = precoAtual;
    }
}
