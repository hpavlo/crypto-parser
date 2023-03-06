package com.example.cryptoparser.dto.mapper;

import com.example.cryptoparser.dto.PriceDto;
import com.example.cryptoparser.model.Price;
import org.springframework.stereotype.Component;

@Component
public class PriceDtoMapper {
    public PriceDto mapToDto(Price price) {
        PriceDto dto = new PriceDto();
        dto.setId(price.getId());
        dto.setLastPrice(price.getLastPrice());
        dto.setFirstCurrency(price.getFirstCurrency());
        dto.setSecondCurrency(price.getSecondCurrency());
        dto.setTime(price.getTime());
        return dto;
    }
}
