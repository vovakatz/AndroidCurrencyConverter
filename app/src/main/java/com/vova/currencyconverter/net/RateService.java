package com.vova.currencyconverter.net;

import android.util.Log;

import com.vova.currencyconverter.AppContext;
import com.vova.currencyconverter.models.Currency;
import com.vova.currencyconverter.models.ExchangeRate;
import com.vova.currencyconverter.models.MessageEvent;

import org.greenrobot.eventbus.EventBus;

import java.math.BigDecimal;
import java.util.ArrayList;
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
    public void PopulateRates()
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
                        AppContext.rates = response.body();
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
    public ArrayList<Currency> GetCurrencies(String base, int baseAmount)
    {
        ArrayList<Currency> currencies = new ArrayList<>();
        if (base == DEFAULT_CURRENCY)
        {
            Iterator it = AppContext.rates.getRates().entrySet().iterator();
            while (it.hasNext())
            {
                Map.Entry pair = (Map.Entry)it.next();
                Currency currency = new Currency();
                currency.setSymbol(pair.getKey().toString());
                currency.setAmount(((BigDecimal) pair.getValue()).multiply(new BigDecimal(baseAmount)));
                currencies.add(currency);
            }
        }
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
