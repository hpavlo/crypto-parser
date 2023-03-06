package com.example.cryptoparser.service.impl;

import com.example.cryptoparser.model.Currency;
import com.example.cryptoparser.model.Price;
import com.example.cryptoparser.repository.PriceRepository;
import com.example.cryptoparser.service.CexioService;
import com.example.cryptoparser.service.RequestApiService;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Service
public class RequestApiServiceImpl implements RequestApiService {
    private final PriceRepository priceRepository;
    private CexioService cexioService;
    @Value("${cexio.external.api.url}")
    private String externalApiUrl;

    public RequestApiServiceImpl(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    @Scheduled(fixedRate = 10_000)
    @Override
    public void loadExternalPrices() {
        loadCurrencyPrice(Currency.BTC);
        loadCurrencyPrice(Currency.ETH);
        loadCurrencyPrice(Currency.XRP);
    }

    private void loadCurrencyPrice(Currency firstCurrency) {
        Call<Price> lastPrice = cexioService.getLastPrice(firstCurrency, Currency.USD);
        try {
            Price price = lastPrice.execute().body();
            if (price != null && !isTheLastPriceInDb(price)) {
                price.setTime(LocalDateTime.now());
                priceRepository.save(price);
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't get last price of "
                    + firstCurrency + " from external API", e);
        }
    }

    private boolean isTheLastPriceInDb(Price price) {
        Optional<Price> priceFromDb = priceRepository
                .findFirstByFirstCurrencyAndSecondCurrencyOrderByTimeDesc(
                        price.getFirstCurrency(), price.getSecondCurrency());
        return priceFromDb.filter(dbPrice ->
                price.getLastPrice().equals(dbPrice.getLastPrice()))
                .isPresent();
    }

    @PostConstruct
    private void generateCexioService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(externalApiUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        cexioService = retrofit.create(CexioService.class);
    }
}
