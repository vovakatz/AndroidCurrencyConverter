package com.vova.currencyconverter.services;

import android.util.Log;

import com.vova.currencyconverter.AppContext;
import com.vova.currencyconverter.models.ExchangeRate;
import com.vova.currencyconverter.models.HistoricalRateEvent;
import com.vova.currencyconverter.models.RateEvent;

import org.greenrobot.eventbus.EventBus;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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

    @Override
    public void populateRates(final Boolean triggerEvent)
    {
        getService().getRates(DEFAULT_CURRENCY).enqueue(new Callback<ExchangeRate>()
        {
            @Override
            public void onResponse(Response<ExchangeRate> response)
            {
                if (response.isSuccess())
                {
                    AppContext.rates = response.body();
                    AppContext.rates.getRates().put(DEFAULT_CURRENCY, new BigDecimal("1"));
                    if (triggerEvent)
                        EventBus.getDefault().post(new RateEvent(true));
                }
                else
                {
                    if (triggerEvent)
                        EventBus.getDefault().post(new RateEvent(false));
                }
            }

            @Override
            public void onFailure(Throwable t)
            {
                if (triggerEvent)
                    EventBus.getDefault().post(new RateEvent(false));
                String message = t.getMessage();
                Log.e("Error occurred", message == null ? "Remote server call returned an error" : message);
            }
        });
    }

    public void populateHistoricRates(final Date date, final String baseCurrency, final String targetCurrency)
    {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        getService().getHistoricRates(sdf.format(date), baseCurrency, targetCurrency).enqueue(new Callback<ExchangeRate>()
        {
            @Override
            public void onResponse(Response<ExchangeRate> response)
            {
                if (response.isSuccess())
                {
                    AppContext.historicRates.add(response.body());
                    EventBus.getDefault().post(new HistoricalRateEvent(true, baseCurrency, targetCurrency));
                    Log.i("HISTORIC RATES", "Service executed successfully for " + sdf.format(date));
                }
                else
                {
                    EventBus.getDefault().post(new HistoricalRateEvent(false, null, null));
                    Log.e("Error occurred", "Remote server call returned an error while getting historical rates");
                    //// TODO: 2/19/16 Cancel the rest of the calls
                }
            }

            @Override
            public void onFailure(Throwable t)
            {
                EventBus.getDefault().post(new HistoricalRateEvent(false, null, null));
                String message = t.getMessage();
                Log.e("Error occurred", message == null ? "Remote server call returned an error" : message);
                //// TODO: 2/19/16 Cancel the rest of the calls
            }
        });
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
