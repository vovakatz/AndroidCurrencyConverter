package com.vova.currencyconverter.net;

import com.vova.currencyconverter.models.ExchangeRate;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IRateApi
{
    @GET("/latest")
    Call<ExchangeRate> getRates(@Query("base") String base);
}
