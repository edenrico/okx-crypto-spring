package ed.cripto.okx_cripto.services;

import ed.cripto.okx_cripto.entity.CriptoCurrency;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CoinGeckoService {

    private static final String COINGECKO_URL = "https://api.coingecko.com/api/v3/simple/price";

    // Retorna preços ao vivo
    public List<CriptoCurrency> getLivePrices() {
        RestTemplate restTemplate = new RestTemplate();
        String ids = "bitcoin,ethereum,dogecoin,xrp";
        String vsCurrencies = "usd";
        String url = COINGECKO_URL + "?ids=" + ids + "&vs_currencies=" + vsCurrencies;

        ResponseEntity<Map<String, Map<String, Double>>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );

        List<CriptoCurrency> criptos = new ArrayList<>();
        if (response.getBody() != null) {
            for (Map.Entry<String, Map<String, Double>> entry : response.getBody().entrySet()) {
                CriptoCurrency cripto = new CriptoCurrency();
                cripto.setNome(entry.getKey());
                cripto.setPrecoAtual(entry.getValue().get("usd"));
                criptos.add(cripto);
            }
        }
        return criptos;
    }
}
