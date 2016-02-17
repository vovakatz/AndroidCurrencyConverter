package com.vova.currencyconverter;

import android.app.Application;
import android.util.Log;

import com.vova.currencyconverter.models.ExchangeRate;
import com.vova.currencyconverter.net.IRateService;
import com.vova.currencyconverter.net.RateService;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AppContext extends Application
{
    public static ExchangeRate rates;

    @Override
    public void onCreate()
    {
        super.onCreate();
        //populateRates();
        scheduleRatesUpdates();
    }

    private void scheduleRatesUpdates()
    {
        ScheduledExecutorService scheduler =
                Executors.newSingleThreadScheduledExecutor();

        scheduler.scheduleAtFixedRate
                (new Runnable()
                {
                    public void run()
                    {
                        if (!RateService.isProcessing)
                        {
                            IRateService service = new RateService();
                            service.populateRates();
                            Log.i("RateService", "Update rates service has just been called!");
                        }
                    }
                }, 0, 15, TimeUnit.MINUTES);
    }

    public void populateRates()
    {
        if (!RateService.isProcessing)
        {
            IRateService service = new RateService();
            service.populateRates();
        }
    }
}
