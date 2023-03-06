package com.example.cryptoparser.repository;

import com.example.cryptoparser.model.Currency;
import com.example.cryptoparser.model.Price;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PriceRepository extends MongoRepository<Price, String> {
    Optional<Price> findFirstByFirstCurrencyAndSecondCurrencyOrderByTimeDesc(
            Currency first, Currency second);

    Optional<Price> findFirstByFirstCurrencyOrderByLastPrice(Currency first);

    Optional<Price> findFirstByFirstCurrencyOrderByLastPriceDesc(Currency first);
}
