package com.example.cryptoparser.service;

import com.example.cryptoparser.model.Currency;
import com.example.cryptoparser.model.Price;

public interface CryptoCurrencyService {
    Price getMinPriceByCurrency(Currency currency);

    Price getMaxPriceByCurrency(Currency currency);
}
