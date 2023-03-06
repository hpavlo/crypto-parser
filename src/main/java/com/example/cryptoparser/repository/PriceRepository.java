package com.example.cryptoparser.repository;

import com.example.cryptoparser.model.Currency;
import com.example.cryptoparser.model.Price;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PriceRepository extends MongoRepository<Price, String> {
    Optional<Price> findFirstByFirstCurrencyAndSecondCurrencyOrderByTimeDesc(
            Currency firstCurrency, Currency secondCurrency);

    Optional<Price> findFirstByFirstCurrencyOrderByLastPrice(Currency firstCurrency);

    Optional<Price> findFirstByFirstCurrencyOrderByLastPriceDesc(Currency firstCurrency);

    List<Price> findAllByFirstCurrencyOrderByLastPrice(Currency firstCurrency, Pageable pageable);
}
