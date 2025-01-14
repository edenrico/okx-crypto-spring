package ed.cripto.okx_cripto.services;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ed.cripto.okx_cripto.entity.CriptoCurrency;
import ed.cripto.okx_cripto.repository.CriptoCurrencyRepository;

@Service
@Transactional
public class CriptoService {

    @Autowired
    private CriptoCurrencyRepository criptoCurrencyRepository;

    public CriptoCurrency createCripto(CriptoCurrency criptoCurrency) {
        return criptoCurrencyRepository.save(criptoCurrency);
    }

    public List<CriptoCurrency> getAllCriptos() {
        return criptoCurrencyRepository.findAll();
    }

    public CriptoCurrency getCriptoById(UUID id) {
        return criptoCurrencyRepository.findById(id).orElse(null);
    }

    public CriptoCurrency updateCripto(CriptoCurrency criptoCurrency) {
        return criptoCurrencyRepository.save(criptoCurrency);
    }

    public void deleteCripto(UUID id) {
        criptoCurrencyRepository.deleteById(id);
    }
}
