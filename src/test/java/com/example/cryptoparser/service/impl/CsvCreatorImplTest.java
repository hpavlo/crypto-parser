package com.example.cryptoparser.service.impl;

import com.example.cryptoparser.model.Currency;
import com.example.cryptoparser.model.Price;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletResponse;

@ExtendWith(MockitoExtension.class)
class CsvCreatorImplTest {
    @InjectMocks
    private CsvCreatorImpl csvCreator;
    @Mock
    private CryptoCurrencyServiceImpl currencyService;

    private static Price minBtcPrice;
    private static Price maxBtcPrice;
    private static Price minEthPrice;
    private static Price maxEthPrice;
    private static Price minXrpPrice;
    private static Price maxXrpPrice;

    @BeforeAll
    static void onStart() {
        minBtcPrice = new Price();
        minBtcPrice.setLastPrice(BigDecimal.valueOf(0.5));
        maxBtcPrice = new Price();
        maxBtcPrice.setLastPrice(BigDecimal.valueOf(0.8));

        minEthPrice = new Price();
        minEthPrice.setLastPrice(BigDecimal.valueOf(0.3));
        maxEthPrice = new Price();
        maxEthPrice.setLastPrice(BigDecimal.valueOf(0.45));

        minXrpPrice = new Price();
        minXrpPrice.setLastPrice(BigDecimal.valueOf(1.3));
        maxXrpPrice = new Price();
        maxXrpPrice.setLastPrice(BigDecimal.valueOf(1.36));
    }

    @Test
    void createResponseWithNormalData() {
        Mockito.when(currencyService.getMinPriceByCurrency(Currency.BTC)).thenReturn(minBtcPrice);
        Mockito.when(currencyService.getMaxPriceByCurrency(Currency.BTC)).thenReturn(maxBtcPrice);
        Mockito.when(currencyService.getMinPriceByCurrency(Currency.ETH)).thenReturn(minEthPrice);
        Mockito.when(currencyService.getMaxPriceByCurrency(Currency.ETH)).thenReturn(maxEthPrice);
        Mockito.when(currencyService.getMinPriceByCurrency(Currency.XRP)).thenReturn(minXrpPrice);
        Mockito.when(currencyService.getMaxPriceByCurrency(Currency.XRP)).thenReturn(maxXrpPrice);

        MockHttpServletResponse response = new MockHttpServletResponse();
        csvCreator.createResponse(response);
        String expectedContentType = "text/plain; charset=utf-8";
        String actualContentType = response.getContentType();
        Assertions.assertEquals(expectedContentType, actualContentType);
        try {
            String expectedContent = """
                    Cryptocurrency Name,Min Price,Max Price\r
                    BTC,0.5,0.8\r
                    ETH,0.3,0.45\r
                    XRP,1.3,1.36\r
                    """;
            String actualContent = response.getContentAsString();
            Assertions.assertEquals(expectedContent, actualContent);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}