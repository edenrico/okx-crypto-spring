package ed.cripto.okx_cripto.controller;

import ed.cripto.okx_cripto.entity.CriptoCurrency;
import ed.cripto.okx_cripto.services.CriptoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/criptos")
public class CriptoController {

    @Autowired
    private CriptoService criptoService;

    @PostMapping("/create")
    public ResponseEntity<CriptoCurrency> createCripto(@RequestBody CriptoCurrency criptoCurrency) {
        CriptoCurrency createdCripto = criptoService.createCripto(criptoCurrency);
        return ResponseEntity.ok(createdCripto);
    }

    @GetMapping
    public ResponseEntity<List<CriptoCurrency>> getAllCriptos() {
        List<CriptoCurrency> criptos = criptoService.getAllCriptos();
        return ResponseEntity.ok(criptos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CriptoCurrency> getCriptoById(@PathVariable UUID id) {
        CriptoCurrency criptoCurrency = criptoService.getCriptoById(id);
        if (criptoCurrency != null) {
            return ResponseEntity.ok(criptoCurrency);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CriptoCurrency> updateCripto(@PathVariable UUID id, @RequestBody CriptoCurrency criptoDetails) {
        CriptoCurrency criptoCurrency = criptoService.getCriptoById(id);
        if (criptoCurrency != null) {
            criptoCurrency.setNome(criptoDetails.getNome());
            criptoCurrency.setPrecoAtual(criptoDetails.getPrecoAtual());
            CriptoCurrency updatedCripto = criptoService.updateCripto(criptoCurrency);
            return ResponseEntity.ok(updatedCripto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCripto(@PathVariable UUID id) {
        criptoService.deleteCripto(id);
        return ResponseEntity.noContent().build();
    }
}
