package com.vova.currencyconverter;

import android.app.Application;

import com.vova.currencyconverter.models.ExchangeRate;
import com.vova.currencyconverter.net.IRateService;
import com.vova.currencyconverter.net.RateService;

public class AppContext extends Application
{
    public static ExchangeRate rates;

    @Override
    public void onCreate()
    {
        super.onCreate();
        PopulateRates();
    }

    public void PopulateRates()
    {
        if (!RateService.isProcessing)
        {
            IRateService service = new RateService();
            service.PopulateRates();
        }
    }
}
