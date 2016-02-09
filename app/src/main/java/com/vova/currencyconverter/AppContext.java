package com.vova.currencyconverter;

import android.app.Application;

import com.vova.currencyconverter.models.ExchangeRate;
import com.vova.currencyconverter.net.IRateApi;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AppContext extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        PopulateRates();
    }

    public void PopulateRates()
    {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://api.fixer.io").build();
        IRateApi rates = restAdapter.create(IRateApi.class);

        rates.getRates("USD", new Callback<ExchangeRate>()
        {
            @Override
            public void success(ExchangeRate exchangeRate, Response response)
            {
                String test = "";
            }

            @Override
            public void failure(RetrofitError error)
            {
                String test = "";
            }
        });
    }
}
