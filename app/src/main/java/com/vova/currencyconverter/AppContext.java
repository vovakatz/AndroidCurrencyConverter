package com.vova.currencyconverter;

import android.app.Application;
import android.util.Log;

import com.vova.currencyconverter.models.ExchangeRate;
import com.vova.currencyconverter.net.IRateService;
import com.vova.currencyconverter.net.RateService;

import net.danlew.android.joda.JodaTimeAndroid;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AppContext extends Application
{
    public static ExchangeRate rates;
    public static ArrayList<ExchangeRate> historicRates = new ArrayList<ExchangeRate>();
    public static int numberOfCallsInitiated;

    @Override
    public void onCreate()
    {
        super.onCreate();
        JodaTimeAndroid.init(this);
        populateRates();
        //scheduleRatesUpdates();
    }

    public void populateRates()
    {
        IRateService service = new RateService();
        service.populateRates();
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
                        service.populateRates();
                        Log.i("RateService", "Update rates service has just been called!");
                    }
                }, 15, 15, TimeUnit.MINUTES);
    }
}
