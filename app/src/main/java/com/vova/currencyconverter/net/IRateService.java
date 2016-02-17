package com.vova.currencyconverter.net;

import com.vova.currencyconverter.models.Currency;

import java.math.BigDecimal;
import java.util.ArrayList;

public interface IRateService
{
    void populateRates();
    ArrayList<String> getCurrenciesList();
    BigDecimal convert (int amount, String fromCurrency, String toCurrency);
}
