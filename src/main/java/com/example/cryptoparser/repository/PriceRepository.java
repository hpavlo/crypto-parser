package com.example.cryptoparser.repository;

import com.example.cryptoparser.model.Currency;
import com.example.cryptoparser.model.Price;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigDecimal;
import java.util.Optional;

public interface PriceRepository extends MongoRepository<Price, String> {
    Optional<Price> findFirstByFirstCurrencyAndSecondCurrencyOrderByTimeDesc
            (Currency first, Currency second);
}
