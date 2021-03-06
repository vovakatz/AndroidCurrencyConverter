package com.vova.currencyconverter.services;

import com.vova.currencyconverter.models.ExchangeRate;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IRateApi
{
    @GET("/latest")
    Call<ExchangeRate> getRates(@Query("base") String base);

    @GET("{date}")
    Call<ExchangeRate> getHistoricRates(@Path("date") String date, @Query("base") String base, @Query("symbols") String target);
}
