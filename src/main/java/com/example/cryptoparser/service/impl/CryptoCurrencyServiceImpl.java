package com.example.cryptoparser.service.impl;

import com.example.cryptoparser.model.Currency;
import com.example.cryptoparser.model.Price;
import com.example.cryptoparser.repository.PriceRepository;
import com.example.cryptoparser.service.CryptoCurrencyService;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class CryptoCurrencyServiceImpl implements CryptoCurrencyService {
    private final PriceRepository priceRepository;

    public CryptoCurrencyServiceImpl(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    @Override
    public Price getMinPriceByCurrency(Currency currency) {
        return priceRepository.findFirstByFirstCurrencyOrderByLastPrice(currency)
                .orElseThrow(() -> new RuntimeException(
                        "Can't find min price by currency " + currency));
    }

    @Override
    public Price getMaxPriceByCurrency(Currency currency) {
        return priceRepository.findFirstByFirstCurrencyOrderByLastPriceDesc(currency)
                .orElseThrow(() -> new RuntimeException(
                        "Can't find max price by currency " + currency));
    }

    @Override
    public List<Price> getHistory(Currency currency, PageRequest pageRequest) {
        return priceRepository.findAllByFirstCurrencyOrderByLastPrice(currency, pageRequest);
    }
}
