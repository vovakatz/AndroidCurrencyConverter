package com.vova.currencyconverter;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.vova.currencyconverter.models.ExchangeRate;
import com.vova.currencyconverter.services.IRateService;
import com.vova.currencyconverter.services.RateService;

import net.danlew.android.joda.JodaTimeAndroid;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AppContext extends Application
{
    private static Context context;
    public static int numberOfCallsInitiated;

    @Override
    public void onCreate()
    {
        super.onCreate();
        context = getApplicationContext();
        JodaTimeAndroid.init(this);
        populateRates();
        scheduleRatesUpdates();
    }

    public static Context getContext()
    {
        return context;
    }

    public void populateRates()
    {
        IRateService service = new RateService();
        service.populateRates(true);
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
                        IRateService service = new RateService();
                        service.populateRates(false);
                        Log.i("RateService", "Update rates service has just been called!");
                    }
                }, 15, 15, TimeUnit.MINUTES);
    }
}
