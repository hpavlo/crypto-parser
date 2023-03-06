package com.example.cryptoparser.controller;

import com.example.cryptoparser.dto.PriceDto;
import com.example.cryptoparser.dto.mapper.PriceDtoMapper;
import com.example.cryptoparser.model.Currency;
import com.example.cryptoparser.model.Price;
import com.example.cryptoparser.service.CryptoCurrencyService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cryptocurrencies")
public class CryptoCurrencyController {
    private final CryptoCurrencyService currencyService;
    private final PriceDtoMapper priceDtoMapper;

    public CryptoCurrencyController(CryptoCurrencyService currencyService,
                                    PriceDtoMapper priceDtoMapper) {
        this.currencyService = currencyService;
        this.priceDtoMapper = priceDtoMapper;
    }

    @GetMapping("minprice")
    @Operation(description = "Get min price by currency name")
    public ResponseEntity<?> getMinPrice(@RequestParam(name = "name") String currencyName) {
        Optional<Currency> currencyOptional = currencyNameValidation(currencyName.toUpperCase());
        if (currencyOptional.isPresent()) {
            Price minPrice = currencyService.getMinPriceByCurrency(currencyOptional.get());
            return ResponseEntity.of(Optional.of(priceDtoMapper.mapToDto(minPrice)));
        }
        return currencyNameBadRequest(currencyName);
    }

    @GetMapping("maxprice")
    @Operation(description = "Get max price by currency name")
    public ResponseEntity<?> getMaxPrice(@RequestParam(name = "name") String currencyName) {
        Optional<Currency> currencyOptional = currencyNameValidation(currencyName.toUpperCase());
        if (currencyOptional.isPresent()) {
            Price maxPrice = currencyService.getMaxPriceByCurrency(currencyOptional.get());
            return ResponseEntity.of(Optional.of(priceDtoMapper.mapToDto(maxPrice)));
        }
        return currencyNameBadRequest(currencyName);
    }

    @GetMapping
    @Operation(description = "Get all history by currency name")
    public ResponseEntity<?> getHistory(
            @RequestParam(name = "name") String currencyName,
            @RequestParam(name = "page", defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "size", defaultValue = "10") Integer pageSize) {
        Optional<Currency> currencyOptional = currencyNameValidation(currencyName.toUpperCase());
        if (currencyOptional.isPresent()) {
            PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
            List<PriceDto> prices = currencyService.getHistory(currencyOptional.get(), pageRequest)
                    .stream()
                    .map(priceDtoMapper::mapToDto)
                    .toList();
            return ResponseEntity.of(Optional.of(prices));
        }
        return currencyNameBadRequest(currencyName);
    }

    private Optional<Currency> currencyNameValidation(String name) {
        if (EnumUtils.isValidEnum(Currency.class, name)
                && !Currency.valueOf(name).equals(Currency.USD)) {
            return Optional.of(Currency.valueOf(name));
        }
        return Optional.empty();
    }

    private ResponseEntity<String> currencyNameBadRequest(String currencyName) {
        return ResponseEntity.badRequest()
                .body("Your currency name " + currencyName + " isn't correct");
    }
}
