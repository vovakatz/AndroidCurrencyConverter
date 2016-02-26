package com.vova.currencyconverter;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.vova.currencyconverter.activities.setting.SettingsActivity;
import com.vova.currencyconverter.models.ExchangeRate;
import com.vova.currencyconverter.services.IRateService;
import com.vova.currencyconverter.services.RateService;
import com.vova.currencyconverter.utils.SharedPreferencesUtils;

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
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

        int syncFrequency = SharedPreferencesUtils.getSyncFrequency();
        if (syncFrequency > 0)
        {
            scheduler.scheduleAtFixedRate
                    (new Runnable()
                    {
                        public void run()
                        {
                            IRateService service = new RateService();
                            service.populateRates(false);
                            Log.i("RateService", "Update rates service has just been called!");
                        }
                    }, syncFrequency, syncFrequency, TimeUnit.MINUTES);
        }
    }
}
