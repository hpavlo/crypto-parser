package com.example.cryptoparser.service.impl;

import com.example.cryptoparser.model.Currency;
import com.example.cryptoparser.model.Price;
import com.example.cryptoparser.service.CryptoCurrencyService;
import com.example.cryptoparser.service.CsvCreator;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import org.springframework.stereotype.Service;

@Service
public class CsvCreatorImpl implements CsvCreator {
    private static final String CSV_HEADER = "Cryptocurrency Name,Min Price,Max Price";
    private static final String CSV_DIVIDER = ",";
    private final CryptoCurrencyService currencyService;

    public CsvCreatorImpl(CryptoCurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @Override
    public void createResponse(HttpServletResponse response) {
        response.setContentType("text/plain; charset=utf-8");
        try (PrintWriter writer = response.getWriter()) {
            writer.println(CSV_HEADER);
            writer.println(createCurrencyStatistic(Currency.BTC));
            writer.println(createCurrencyStatistic(Currency.ETH));
            writer.println(createCurrencyStatistic(Currency.XRP));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String createCurrencyStatistic(Currency currency) {
        StringBuilder stringBuilder = new StringBuilder(currency.toString());
        stringBuilder.append(CSV_DIVIDER);
        Price minPrice = currencyService.getMinPriceByCurrency(currency);
        stringBuilder.append(minPrice.getLastPrice())
                .append(CSV_DIVIDER);
        Price maxPrice = currencyService.getMaxPriceByCurrency(currency);
        stringBuilder.append(maxPrice.getLastPrice());
        return stringBuilder.toString();
    }
}
