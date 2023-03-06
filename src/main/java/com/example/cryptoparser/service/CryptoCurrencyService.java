package com.example.cryptoparser.service;

import com.example.cryptoparser.model.Currency;
import com.example.cryptoparser.model.Price;
import java.util.List;
import org.springframework.data.domain.PageRequest;

public interface CryptoCurrencyService {
    Price getMinPriceByCurrency(Currency currency);

    Price getMaxPriceByCurrency(Currency currency);

    List<Price> getHistory(Currency currency, PageRequest pageRequest);
}
