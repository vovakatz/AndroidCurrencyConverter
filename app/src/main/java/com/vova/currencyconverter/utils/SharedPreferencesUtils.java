package com.vova.currencyconverter.utils;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vova.currencyconverter.AppContext;
import com.vova.currencyconverter.Constants;
import com.vova.currencyconverter.models.ExchangeRate;
import java.util.ArrayList;
import java.util.List;

public class SharedPreferencesUtils
{

    public static ExchangeRate getExchangeRates()
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(AppContext.getContext());
        String json = sharedPreferences.getString(Constants.EXCHANGE_RATES, "");
        Gson gson = new Gson();
        ExchangeRate exchangeRate = gson.fromJson(json, ExchangeRate.class);
        return exchangeRate;
    }

    public static void setExchangeRates(ExchangeRate exchageRate)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(AppContext.getContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(exchageRate);
        editor.putString(Constants.EXCHANGE_RATES, json);
        editor.commit();
    }

    public static ArrayList<ExchangeRate> getHistoricalExchangeRates()
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(AppContext.getContext());
        String json = sharedPreferences.getString(Constants.HISTORICAL_EXCHANGE_RATES, "");
        Gson gson = new Gson();
        ArrayList<ExchangeRate> exchangeRates = gson.fromJson(json, new TypeToken<List<ExchangeRate>>(){}.getType());
        return exchangeRates == null ? new ArrayList<ExchangeRate>() : exchangeRates;
    }

    public static void setHistoricalExchangeRates(ArrayList<ExchangeRate> exchageRates)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(AppContext.getContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(exchageRates, new TypeToken<List<ExchangeRate>>(){}.getType());
        editor.putString(Constants.HISTORICAL_EXCHANGE_RATES, json);
        editor.commit();
    }
}
