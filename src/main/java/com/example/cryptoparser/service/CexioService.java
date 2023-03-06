package com.example.cryptoparser.service;

import com.example.cryptoparser.model.Currency;
import com.example.cryptoparser.model.Price;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CexioService {
    @GET("/api/last_price/{symbol1}/{symbol2}")
    Call<Price> getLastPrice(@Path("symbol1") Currency firstCurrency,
                             @Path("symbol2") Currency secondCurrency);
}
