package com.vova.currencyconverter.net;

import android.util.Log;

import com.vova.currencyconverter.AppContext;
import com.vova.currencyconverter.models.Currency;
import com.vova.currencyconverter.models.ExchangeRate;
import com.vova.currencyconverter.models.MessageEvent;

import org.greenrobot.eventbus.EventBus;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RateService implements IRateService
{
    private final String BASE_URL = "http://api.fixer.io";
    private final String DEFAULT_CURRENCY = "USD";

    private static IRateApi service;
    public static boolean isProcessing;

    @Override
    public void populateRates()
    {
        if (isProcessing == false)
        {
            isProcessing = true;
            getService().getRates(DEFAULT_CURRENCY).enqueue(new Callback<ExchangeRate>()
            {
                @Override
                public void onResponse(Response<ExchangeRate> response)
                {
                    if (response.isSuccess())
                    {
                        isProcessing = false;
                        AppContext.rates = response.body();
                        AppContext.rates.getRates().put(DEFAULT_CURRENCY, new BigDecimal("1"));
                        EventBus.getDefault().post(new MessageEvent(true));
                    }
                    else
                    {
                        isProcessing = false;
                        EventBus.getDefault().post(new MessageEvent(false));
                    }
                }

                @Override
                public void onFailure(Throwable t)
                {
                    Log.e("Error occured", t.getMessage());
                    EventBus.getDefault().post(new MessageEvent(false));
                    isProcessing = false;
                }
            });
        }
    }

    @Override
    public BigDecimal convert (int amount, String fromCurrency, String toCurrency)
    {
        BigDecimal fromRate = AppContext.rates.getRates().get(fromCurrency);
        BigDecimal amountInUSD = new BigDecimal(amount).divide(fromRate, 2, RoundingMode.HALF_UP);

        BigDecimal toRate = AppContext.rates.getRates().get(toCurrency);
        BigDecimal amountInToCurrency = amountInUSD.multiply(toRate);

        return amountInToCurrency;
    }

    @Override
    public ArrayList<String> getCurrenciesList()
    {
        ArrayList<String> currencies = new ArrayList<String>();
        Iterator it = AppContext.rates.getRates().entrySet().iterator();
        while (it.hasNext())
        {
            Map.Entry pair = (Map.Entry)it.next();
            currencies.add(pair.getKey().toString());
        }
        Collections.sort(currencies);
        return currencies;
    }

    private IRateApi getService()
    {
        if (service == null)
        {
            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build();
            service = retrofit.create(IRateApi.class);
            return service;
        }
        else
        {
            return service;
        }
    }
}
