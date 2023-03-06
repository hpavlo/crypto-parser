package com.example.cryptoparser.dto;

import com.example.cryptoparser.model.Currency;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PriceDto {
    private String id;
    private BigDecimal lastPrice;
    private Currency firstCurrency;
    private Currency secondCurrency;
    private LocalDateTime time;
}
