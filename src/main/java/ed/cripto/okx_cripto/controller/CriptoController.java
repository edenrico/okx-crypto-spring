package ed.cripto.okx_cripto.controller;

import ed.cripto.okx_cripto.entity.CriptoCurrency;
import ed.cripto.okx_cripto.services.CoinGeckoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/criptos")
public class CriptoController {

    @Autowired
    private CoinGeckoService coinGeckoService;

    @GetMapping("/live-prices")
    public ResponseEntity<List<CriptoCurrency>> getLivePrices() {
        List<CriptoCurrency> criptos = coinGeckoService.getLivePrices();
        return ResponseEntity.ok(criptos);
    }
}
