package com.vova.currencyconverter.net;

import com.vova.currencyconverter.models.Currency;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

public interface IRateService
{
    void populateRates();
    void populateHistoricRates(Date date, String baseCurrency, String targetCurrency);
    ArrayList<String> getCurrenciesList();
    BigDecimal convert (int amount, String fromCurrency, String toCurrency);
}
