package com.vova.currencyconverter.net;

import com.vova.currencyconverter.models.ExchangeRate;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public interface IRateApi
{
    @GET("/latest")
    public void getRates(@Query("base") String base, Callback<ExchangeRate> response);
}
