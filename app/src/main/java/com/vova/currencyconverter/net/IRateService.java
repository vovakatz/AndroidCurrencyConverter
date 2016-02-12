package com.vova.currencyconverter.net;

import com.vova.currencyconverter.models.Currency;

import java.util.ArrayList;

public interface IRateService
{
    void PopulateRates();

    ArrayList<Currency> GetCurrencies(String base, int baseAmount);
}
